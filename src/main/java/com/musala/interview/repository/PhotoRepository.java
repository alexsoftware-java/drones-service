package com.musala.interview.repository;

import com.musala.interview.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<ImageEntity, Long> {
}
