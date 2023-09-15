package com.musala.interview.repository;

import com.musala.interview.dto.State;
import com.musala.interview.entity.DroneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DronesRepository extends JpaRepository<DroneEntity, Long> {
    Optional<DroneEntity> findBySerialNumber(String serialNumber);

    List<DroneEntity> findByStateIn(List<State> states);

    @Transactional
    @Modifying
    @Query("update drones d set d.state = ?1 where d.serialNumber = ?2")
    int changeDroneStateBySN(State state, String serialNumber);
}
