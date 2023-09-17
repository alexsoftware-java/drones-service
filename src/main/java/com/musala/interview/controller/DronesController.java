package com.musala.interview.controller;

import com.musala.interview.controller.contract.DronesControllerAPI;
import com.musala.interview.dto.DroneDto;
import com.musala.interview.service.DroneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DronesController implements DronesControllerAPI {
    private final DroneService dispatcherService;

    @Override
    public DroneDto addDrone(DroneDto requestDto) {
        return dispatcherService.addDrone(requestDto);
    }

    @Override
    public void deleteDrone(String serialNumber) {
        dispatcherService.deleteDrone(serialNumber);
    }

    @Override
    public List<DroneDto> listAvailableDrones() {
        return dispatcherService.getAvailableDrones();
    }

    @Override
    public Integer getDroneBatteryLevelBySN(String serialNumber) {
        return dispatcherService.getDroneBatteryLevelBySN(serialNumber);
    }
}
