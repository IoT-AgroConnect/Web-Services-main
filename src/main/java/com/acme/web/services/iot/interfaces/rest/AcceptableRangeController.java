package com.acme.web.services.iot.interfaces.rest;

import com.acme.web.services.iot.domain.model.commands.UpdateAcceptableRangeCommand;
import com.acme.web.services.iot.domain.services.AcceptableRangeCommandService;
import com.acme.web.services.iot.domain.services.AcceptableRangeQueryService;
import com.acme.web.services.iot.interfaces.rest.resources.AcceptableRangeResource;
import com.acme.web.services.iot.interfaces.rest.transform.AcceptableRangeResourceFromEntityAssembler;
import com.acme.web.services.iot.interfaces.rest.transform.CreateAcceptableRangeResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/acceptable-ranges")
@Tag(name = "Acceptable Ranges for Sensors", description = "Manage acceptable ranges for environmental conditions in cages")
public class AcceptableRangeController {

    private final AcceptableRangeCommandService commandService;
    private final AcceptableRangeQueryService queryService;

    public AcceptableRangeController(
            AcceptableRangeCommandService commandService,
            AcceptableRangeQueryService queryService
    ) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Obtener todos los rangos aceptables",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Rango aceptable creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error al crear el rango aceptable"),
                    @ApiResponse(responseCode = "401", description = "No autorizado")
            })
    @GetMapping
    public ResponseEntity<List<AcceptableRangeResource>> getAll() {
        var ranges = queryService.handleGetAllAcceptableRangesQuery()
                .stream()
                .map(AcceptableRangeResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ok(ranges);
    }

    @Operation(summary = "Obtener rango aceptable por cage ID",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Rango aceptable creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error al crear el rango aceptable"),
                    @ApiResponse(responseCode = "401", description = "No autorizado")
            })
    @GetMapping("/by-cage/{cageId}")
    public ResponseEntity<AcceptableRangeResource> getByCageId(@PathVariable Long cageId) {
        return queryService.handleGetAcceptableRangeByCageIdQuery(cageId)
                .map(AcceptableRangeResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @Operation(summary = "Crear rango aceptable",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Rango aceptable creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error al crear el rango aceptable"),
                    @ApiResponse(responseCode = "401", description = "No autorizado")
            })
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody AcceptableRangeResource resource) {
        var command = CreateAcceptableRangeResourceFromEntityAssembler.toCommandFromResource(resource);
        Long createdId = commandService.handle(command);
        return ResponseEntity.ok(createdId);
    }

    @Operation(summary = "Editar rango aceptable",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Rango aceptable creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error al crear el rango aceptable"),
                    @ApiResponse(responseCode = "401", description = "No autorizado")
            })
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AcceptableRangeResource resource) {
        var command = new UpdateAcceptableRangeCommand(
                id,
                resource.minTemperature(),
                resource.maxTemperature(),
                resource.minHumidity(),
                resource.maxHumidity(),
                resource.minCo2(),
                resource.maxCo2(),
                resource.minWaterQuality(),
                resource.maxWaterQuality(),
                resource.minWaterQuantity(),
                resource.maxWaterQuantity(),
                resource.cageId()
        );

        var updated = commandService.handle(command);
        if (updated.isPresent()) {
            return noContent().build();
        } else {
            return notFound().build();
        }
    }
}
