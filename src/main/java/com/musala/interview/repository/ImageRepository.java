package com.musala.interview.repository;

import com.musala.interview.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    Optional<ImageEntity> findByGoodsId(long id);
}
