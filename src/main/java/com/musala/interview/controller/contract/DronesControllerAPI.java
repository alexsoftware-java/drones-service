package com.musala.interview.controller.contract;

import com.musala.interview.dto.DroneDto;
import com.musala.interview.dto.MedicationDto;
import com.musala.interview.validator.ValidDrone;
import com.musala.interview.validator.ValidMedication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Drones dispatcher API", description = "API allowing Create and Read operations on drones and theirs deliveries")
@RequestMapping("/api/v1/drones")
@Validated
public interface DronesControllerAPI {
    @Operation(summary = "Get a list of drones available for delivery")
    @GetMapping
    List<DroneDto> listAvailableDrones();

    @Operation(summary = "Get drone battery level by drone serialNumber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
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

    @Operation(summary = "Get medication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all medication on board", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Drone is not found", content = @Content)
    })
    @GetMapping(value = "/{serialNumber}/medication")
    List<MedicationDto> getMedicationBySN(@PathVariable("serialNumber") String serialNumber);

    @Operation(summary = "Add medication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all medication on board",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = """
                                    [
                                     {
                                        "id":4,
                                        "name":"ASPIRIN",
                                        "weight":100,
                                        "code":"ASP_1",
                                        "imageId":null
                                     }
                                    ]""")}
                    )}),
            @ApiResponse(responseCode = "400", description = "Drone is not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Request validation failed", content = @Content)
    })
    @PostMapping(value = "/{serialNumber}/medication")
    List<MedicationDto> addMedicationBySN(@PathVariable("serialNumber") String serialNumber,
                                          @ValidMedication @Parameter(description = "JSON represents medications expected to be added") @RequestBody
                                          MedicationDto medicationRequestDto);

    @Operation(summary = "Add image of medication")
    @PostMapping(value = "/{serialNumber}/medication/{medicationId}/image", consumes = MediaType.IMAGE_JPEG_VALUE)
    void addImage(@NotNull @PathVariable("medicationId") Long medicationId,
                  MultipartFile image);

    @Operation(summary = "Get medication image")
    @GetMapping(value = "/{serialNumber}/medication/{medicationId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    byte[] getImage(@NotNull @PathVariable("medicationId") Long medicationId);
}
