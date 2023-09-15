package com.musala.interview.service;

import com.musala.interview.converter.GoodsEntityToMedicationDtoConverter;
import com.musala.interview.dto.MedicationDto;
import com.musala.interview.dto.State;
import com.musala.interview.entity.DroneEntity;
import com.musala.interview.entity.GoodsEntity;
import com.musala.interview.exception.DispatcherException;
import com.musala.interview.repository.DronesRepository;
import com.musala.interview.repository.GoodsRepository;
import com.musala.interview.utils.GoodsTypes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MedicationService {
    private final GoodsRepository goodsRepository;
    private final DronesRepository dronesRepository;
    private final GoodsEntityToMedicationDtoConverter converter;

    public List<MedicationDto> getDroneMedication(String droneSN) {
        var medications = goodsRepository.findByDroneSerialNumber(droneSN);
        log.debug("Get drone medication: {} entries found by droneSn {}", medications.map(List::size).orElse(0), droneSN);
        return medications.map(goodsEntities -> goodsEntities.stream().map(converter::convert).toList()).orElse(Collections.emptyList());
    }

    /**
     * Creates medication entity
     *
     * @param requestDto validated by {@link com.musala.interview.validator.MedicationValidator}
     * @return List of all medications for the drone
     */
    public List<MedicationDto> addMedication(String droneSN, MedicationDto requestDto) {
        log.debug("Adding medication to drone {}, medication request: {}", droneSN, requestDto);
        var drone = dronesRepository.findBySerialNumber(droneSN);
        if (drone.isEmpty()) {
            throw new DispatcherException("Drone with SN %s can't be found! Medication can't be added".formatted(droneSN));
        }
        checkIfMedicationSuitsDrone(droneSN, requestDto, drone.get());
        var medication = new GoodsEntity();
        medication.setGoodsType(GoodsTypes.MEDICATIONS);
        medication.setName(requestDto.getName());
        medication.setCode(requestDto.getCode());
        medication.setWeight(requestDto.getWeight());
        medication.setDrone(drone.get());
        var createdMedication = goodsRepository.saveAndFlush(medication);
        log.debug("Medication added to drone {}, medication id {}", droneSN, createdMedication.getId());
        return getDroneMedication(droneSN);
    }

    /**
     * Throws business exception if drone state is not IDLE or weight limit well be exceeded by the loading
     */
    private void checkIfMedicationSuitsDrone(String droneSN, MedicationDto requestDto, DroneEntity drone){
        if (!State.IDLE.equals(drone.getState())) {
            throw new DispatcherException("Drone with SN %s is not available! Medication can't be added".formatted(droneSN));
        }
        int allMedicationOnBoardWeight = getDroneMedication(droneSN).stream().mapToInt(MedicationDto::getWeight).sum();
        if ((allMedicationOnBoardWeight + requestDto.getWeight()) > drone.getWeightLimit()) {
            throw new DispatcherException("Medication can't be added to drone with SN %s, as weight limit of %d gr will be exceeded!".formatted(droneSN, drone.getWeightLimit()));
        }
    }

}
