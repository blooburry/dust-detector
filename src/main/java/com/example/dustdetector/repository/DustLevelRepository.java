package com.example.dustdetector.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dustdetector.model.DustLevel;

public interface DustLevelRepository extends JpaRepository<DustLevel, Integer> {
    DustLevel save(DustLevel dustLevel);
}
