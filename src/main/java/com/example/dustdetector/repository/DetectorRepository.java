package com.example.dustdetector.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dustdetector.model.Detector;

public interface DetectorRepository extends JpaRepository<Detector, Integer> {
    Detector save(Detector detector);
}
