package com.example.dustdetector.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.dustdetector.model.Detector;

public interface DetectorRepository extends JpaRepository<Detector, Integer> {
    Detector save(Detector detector);

    @Query("SELECT d FROM Detector d WHERE d.userId = :userId")    
    List<Detector> findDetectorsByUserId(@Param("userId") Integer userId);
}
