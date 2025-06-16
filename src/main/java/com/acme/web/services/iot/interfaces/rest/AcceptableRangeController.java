package com.acme.web.services.iot.interfaces.rest;
import com.acme.web.services.iot.domain.model.commands.CreateAcceptableRangeCommand;
import com.acme.web.services.iot.domain.model.commands.UpdateAcceptableRangeCommand;
import com.acme.web.services.iot.domain.services.AcceptableRangeCommandService;
import com.acme.web.services.iot.domain.services.AcceptableRangeQueryService;
import com.acme.web.services.iot.interfaces.rest.resources.AcceptableRangeResource;
import com.acme.web.services.iot.interfaces.rest.transform.AcceptableRangeResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/acceptable-ranges")
@Tag(name = "Acceptable Ranges", description = "Acceptable Range Management Endpoints")
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

    @GetMapping
    public ResponseEntity<List<AcceptableRangeResource>> getAll() {
        var ranges = queryService.handleGetAllAcceptableRangesQuery()
                .stream()
                .map(AcceptableRangeResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ok(ranges);
    }

    @GetMapping("/by-cage/{cageId}")
    public ResponseEntity<AcceptableRangeResource> getByCageId(@PathVariable Long cageId) {
        return queryService.handleGetAcceptableRangeByCageIdQuery(cageId)
                .map(AcceptableRangeResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AcceptableRangeResource resource) {
        var command = new CreateAcceptableRangeCommand(
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
                resource.cageId());

        Long createdId = commandService.handle(command);
        return created(URI.create("/api/v1/acceptable-ranges/" + createdId)).build();
    }

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
