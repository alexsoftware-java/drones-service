package com.m.interview.interview.repository;

import com.m.interview.interview.dto.State;
import com.m.interview.interview.entity.DroneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DronesRepository extends JpaRepository<DroneEntity, Long> {
    Optional<DroneEntity> findBySerialNumber(String serialNumber);

    List<DroneEntity> findByStateIn(List<State> states);
}
