package com.musala.interview.repository;

import com.musala.interview.dto.State;
import com.musala.interview.entity.DroneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DronesRepository extends JpaRepository<DroneEntity, Long> {
    List<DroneEntity> findAllBySerialNumber(String serialNumber);

    Optional<DroneEntity> findBySerialNumber(String serialNumber);

    List<DroneEntity> findByStateIn(List<State> states);
}
