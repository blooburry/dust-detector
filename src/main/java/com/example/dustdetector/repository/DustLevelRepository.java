package com.example.dustdetector.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.dustdetector.model.DustLevel;

public interface DustLevelRepository extends JpaRepository<DustLevel, Integer> {
    DustLevel save(DustLevel dustLevel);

    @Query("SELECT dl FROM DustLevel dl WHERE dl.detector.user.id = :userId")
    List<DustLevel> findAllByUserId(@Param("userId") Integer userId);
}
