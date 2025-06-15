package com.acme.web.services.iot.interfaces.rest;

import com.acme.web.services.iot.domain.model.aggregates.FeedingSchedule;
import com.acme.web.services.iot.domain.model.commands.HandleUpdateAllSchedules;
import com.acme.web.services.iot.domain.services.FeedingScheduleCommandService;
import com.acme.web.services.iot.domain.services.FeedingScheduleQueryService;
import com.acme.web.services.iot.interfaces.rest.resources.FeedingScheduleResource;
import com.acme.web.services.iot.interfaces.rest.resources.GlobalScheduleUpdateResource;
import com.acme.web.services.iot.interfaces.rest.transform.CreateFeedingScheduleCommandFromResourceAssembler;
import com.acme.web.services.iot.interfaces.rest.transform.FeedingScheduleResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/v1/feeding-schedules", produces = "application/json")
@Tag(name = "Feeding Schedules", description = "Feeding Schedule Management Endpoints")
public class FeedingScheduleController {

    private final FeedingScheduleCommandService commandService;
    private final FeedingScheduleQueryService queryService;

    public FeedingScheduleController(FeedingScheduleCommandService commandService,
                                     FeedingScheduleQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    // ✅ POST: crear nuevo horario (individual o global)
    @Operation(
            summary = "Crear horario de alimentación para una jaula o todas",
            description = """
        Este endpoint crea un horario de alimentación para una jaula específica o para todas las jaulas.

        • Si deseas asignar el horario a una sola jaula, proporciona su cageId y deja applyToAll en false.

        • Si deseas asignarlo a todas las jaulas que aún no tienen un horario, pon cageId en 0 y applyToAll en true.

        Ejemplo para todas las jaulas:
        {
          "cageId": 0,
          "morningTime": "07:00",
          "eveningTime": "18:30",
          "applyToAll": true
        }
        """
    )
    @PostMapping
    public ResponseEntity<?> create(@RequestBody FeedingScheduleResource resource) {
        var command = CreateFeedingScheduleCommandFromResourceAssembler.toCommandFromResource(resource);
        Long id = commandService.handle(command);

        // Si el id es 0 y applyToAll es true, significa que se aplica a todas las jaulas
        if (resource.applyToAll() || id == 0L) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of("message", "✅ Horario global aplicado a todas las jaulas sin duplicar."));
        }

        return queryService.handleGetFeedingScheduleByIdQuery(id)
                .map(FeedingScheduleResourceFromEntityAssembler::toResourceFromEntity)
                .map(createdResource ->
                        ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(createdResource)
                )
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }


    // ✅ PUT: actualizar horario existente
    @PutMapping("/{id}")
    public ResponseEntity<FeedingScheduleResource> update(
            @PathVariable Long id,
            @RequestBody FeedingScheduleResource resource) {

        return commandService.handle(new com.acme.web.services.iot.domain.model.commands.UpdateFeedingScheduleCommand(
                        id,
                        java.time.LocalTime.parse(resource.morningTime()),
                        java.time.LocalTime.parse(resource.eveningTime())))
                .map(FeedingScheduleResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }

    // ✅ GET: listar todos los horarios
    @GetMapping
    public ResponseEntity<List<FeedingScheduleResource>> getAll() {
        List<FeedingSchedule> schedules = queryService.handleGetAllFeedingSchedulesQuery();
        List<FeedingScheduleResource> resources = schedules.stream()
                .map(FeedingScheduleResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ok(resources);
    }

    @GetMapping("/global-summary")
    public ResponseEntity<FeedingScheduleResource> getMostRecentGlobalSchedule() {
        List<FeedingSchedule> schedules = queryService.handleGetAllFeedingSchedulesQuery();
        if (schedules.isEmpty()) return ResponseEntity.noContent().build();

        FeedingSchedule latest = schedules.stream()
                .max(Comparator.comparing(FeedingSchedule::getCreatedAt)) // usa tu campo de auditoría
                .orElseThrow();

        FeedingScheduleResource resource = FeedingScheduleResourceFromEntityAssembler.toResourceFromEntity(latest);
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/global")
    public ResponseEntity<?> updateAll(@RequestBody GlobalScheduleUpdateResource resource) {
        commandService.handle(new HandleUpdateAllSchedules(
                LocalTime.parse(resource.morningTime()),
                LocalTime.parse(resource.eveningTime())
        ));
        return ResponseEntity.ok(Map.of("message", "✅ Horario actualizado en todas las jaulas"));
    }
}