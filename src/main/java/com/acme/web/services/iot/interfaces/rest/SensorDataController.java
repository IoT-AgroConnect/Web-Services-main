package com.acme.web.services.iot.interfaces.rest;

import com.acme.web.services.iot.domain.model.aggregates.SensorData;
import com.acme.web.services.iot.domain.model.commands.CreateSensorDataCommand;
import com.acme.web.services.iot.domain.model.commands.UpdateSensorDataCommand;
import com.acme.web.services.iot.domain.model.commands.DeleteSensorDataCommand;
import com.acme.web.services.iot.domain.model.queries.GetAllSensorDataQuery;
import com.acme.web.services.iot.domain.model.queries.GetSensorDataByCageIdQuery;
import com.acme.web.services.iot.domain.model.queries.GetSensorDataByIdQuery;
import com.acme.web.services.iot.domain.services.SensorDataCommandService;
import com.acme.web.services.iot.domain.services.SensorDataQueryService;
import com.acme.web.services.iot.interfaces.rest.resources.SensorDataResource;
import com.acme.web.services.iot.interfaces.rest.transform.SensorDataResourceFromEntityAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/iot/sensor-data")
@Tag(name = "Sensor Data")
public class SensorDataController {

    private final SensorDataCommandService commandService;
    private final SensorDataQueryService queryService;

    public SensorDataController(SensorDataCommandService commandService, SensorDataQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Obtener todos los datos de sensores",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Datos obtenidos exitosamente")
            })
    @GetMapping
    public ResponseEntity<List<SensorDataResource>> getAllSensorData() {
        List<SensorData> sensorDataList = queryService.handle(new GetAllSensorDataQuery());
        List<SensorDataResource> resources = sensorDataList.stream()
                .map(SensorDataResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Obtener datos de sensor por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Datos del sensor encontrados"),
                    @ApiResponse(responseCode = "404", description = "Datos del sensor no encontrados")
            })
    @GetMapping("/{id}")
    public ResponseEntity<SensorDataResource> getSensorDataById(@PathVariable Long id) {
        return queryService.handle(new GetSensorDataByIdQuery(id))
                .map(SensorDataResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener datos de sensor por ID de jaula (cageId)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Datos del sensor encontrados para la jaula"),
                    @ApiResponse(responseCode = "404", description = "No se encontraron datos para la jaula")
            })
    @GetMapping("/by-cage/{cageId}")
    public ResponseEntity<List<SensorDataResource>> getSensorDataByCageId(@PathVariable Long cageId) {
        var sensorDataList = queryService.handle(new GetSensorDataByCageIdQuery(cageId));
        if (sensorDataList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resources = sensorDataList.stream()
                .map(SensorDataResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }


    @PostMapping
    @Operation(summary = "Crear nuevo dato de sensor",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Dato de sensor creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error al crear dato del sensor")
            })
    public ResponseEntity<?> create(@RequestBody CreateSensorDataCommand command) {
        try {
            Long id = commandService.handle(command);

            return queryService.handleGetSensorDataByIdQuery(id)
                    .map(SensorDataResourceFromEntityAssembler::toResourceFromEntity)
                    .map(resource ->
                            ResponseEntity
                                    .status(HttpStatus.CREATED)
                                    .body(resource)
                    )
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error al crear el dato del sensor", "details", e.getMessage()));
        }
    }


    @Operation(summary = "Actualizar dato de sensor por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dato de sensor actualizado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Dato de sensor no encontrado")
            })
    @PutMapping("/{id}")
    public ResponseEntity<SensorDataResource> updateSensorData(@PathVariable Long id, @RequestBody UpdateSensorDataCommand command) {
        var updated = commandService.handle(command);
        return updated.map(SensorDataResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar dato de sensor por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dato de sensor eliminado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Dato de sensor no encontrado")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSensorData(@PathVariable Long id) {
        var deleted = commandService.handle(new DeleteSensorDataCommand(id));
        return deleted.isPresent() ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
