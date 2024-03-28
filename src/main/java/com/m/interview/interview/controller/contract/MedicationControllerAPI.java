package com.m.interview.interview.controller.contract;

import com.m.interview.interview.dto.MedicationDto;
import com.m.interview.interview.validator.ValidMedication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Medication control API", description = "API allowing to control drone delivery")
@RequestMapping("/api/v1/drones/{serialNumber}/medication")
@Validated
public interface MedicationControllerAPI {
    @Operation(summary = "Get medication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all medications on board", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Drone is not found", content = @Content)
    })
    @GetMapping
    List<MedicationDto> getMedicationBySN(@PathVariable("serialNumber") String serialNumber);

    @Operation(summary = "Add medication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all medications on board",
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
    @PostMapping
    List<MedicationDto> addMedicationBySN(@PathVariable("serialNumber") String serialNumber,
                                          @ValidMedication @Parameter(description = "JSON represents medications expected to be added") @RequestBody
                                          MedicationDto medicationRequestDto);

    @Operation(summary = "Add image of medication. PNG format supported")
    @PostMapping(value = "/{medicationId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void addImage(@NotNull @PathVariable("medicationId") Long medicationId,
                  MultipartFile image);

    @Operation(summary = "Get medication image")
    @GetMapping(value = "/{medicationId}/image", produces = MediaType.IMAGE_PNG_VALUE)
    byte[] getImage(@NotNull @PathVariable("medicationId") Long medicationId);
}
