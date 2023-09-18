package com.musala.interview.controller.contract;

import com.musala.interview.dto.DroneDto;
import com.musala.interview.validator.ValidDrone;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Drones dispatcher API", description = "API allowing CRUD operations on drones")
@RequestMapping("/api/v1/drones")
@Validated
public interface DronesControllerAPI {
    @Operation(summary = "Get a list of drones available for delivery")
    @GetMapping
    List<DroneDto> listAvailableDrones();

    @Operation(summary = "Get drone battery level by drone serialNumber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Drone battery capacity (%)",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))}),
            @ApiResponse(responseCode = "400", description = "Drone is not found by given serialNumber",
                    content = @Content)
    })
    @GetMapping(value = "/{serialNumber}/battery")
    Integer getDroneBatteryLevelBySN(@Parameter(description = "Drone serialNumber") @PathVariable("serialNumber") String serialNumber);

    @Operation(summary = "Add drone under dispatcher control")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added drone",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = """
                                    {
                                        "serialNumber":"1200000133013",
                                        "model":"Lightweight",
                                        "weightLimit":500,
                                        "batteryCapacity":100,
                                        "state":"IDLE"
                                    }""")},
                            schema = @Schema(implementation = DroneDto.class))}),
            @ApiResponse(responseCode = "400", description = "Request validation failed", content = @Content)
    })
    @PostMapping
    DroneDto addDrone(@ValidDrone @Parameter(description = "JSON represents the drone expected to be added") @RequestBody DroneDto droneRequestDto);

    @Operation(summary = "Delete drone from dispatcher control")
    @DeleteMapping(value = "/{serialNumber}")
    void deleteDrone(@PathVariable("serialNumber") String serialNumber);
}
