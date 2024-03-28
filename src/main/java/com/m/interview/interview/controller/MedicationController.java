package com.m.interview.interview.controller;

import com.m.interview.interview.controller.contract.MedicationControllerAPI;
import com.m.interview.interview.dto.MedicationDto;
import com.m.interview.interview.service.ImageService;
import com.m.interview.interview.service.MedicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MedicationController implements MedicationControllerAPI {
    private final MedicationService medicationService;
    private final ImageService imageService;

    @Override
    public List<MedicationDto> getMedicationBySN(String droneSerialNumber) {
        return medicationService.getDroneMedication(droneSerialNumber);
    }

    @Override
    public List<MedicationDto> addMedicationBySN(String droneSerialNumber, MedicationDto requestDto) {
        return medicationService.addMedication(droneSerialNumber, requestDto);
    }

    @Override
    public void addImage(Long medicationId, MultipartFile image) {
        imageService.addImage(medicationId, image);
    }

    @Override
    public byte[] getImage(Long medicationId) {
        return imageService.getImage(medicationId);
    }
}
