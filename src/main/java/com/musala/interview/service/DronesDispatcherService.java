package com.musala.interview.service;

import com.musala.interview.dto.DroneDto;
import com.musala.interview.dto.MedicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DronesDispatcherService {
    public Optional<List<DroneDto>> getAvailableDrones() {
        return Optional.of(List.of(new DroneDto()));
    }

    public Optional<List<MedicationDto>> getDroneMedication(String droneSN) {
        return Optional.of(List.of(new MedicationDto()));
    }
}
