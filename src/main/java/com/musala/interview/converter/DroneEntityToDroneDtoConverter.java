package com.musala.interview.converter;

import com.musala.interview.dto.DroneDto;
import com.musala.interview.entity.DroneEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DroneEntityToDroneDtoConverter implements Converter<DroneEntity, DroneDto> {
    @Override
    public DroneDto convert(DroneEntity source) {
        var droneDto = new DroneDto();
        droneDto.setSerialNumber(source.getSerialNumber());
        droneDto.setModel(source.getModel());
        droneDto.setState(source.getState());
        droneDto.setBatteryCapacity(source.getBatteryCapacity());
        droneDto.setWeightLimit(source.getWeightLimit());
        return droneDto;
    }
}
