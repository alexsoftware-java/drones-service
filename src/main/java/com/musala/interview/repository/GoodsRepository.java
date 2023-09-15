package com.musala.interview.repository;

import com.musala.interview.entity.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodsRepository extends JpaRepository<GoodsEntity, Long> {
    Optional<List<GoodsEntity>> findByDroneSerialNumber(String droneSerialNumber);
}
