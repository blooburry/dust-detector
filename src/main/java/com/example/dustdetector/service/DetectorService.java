package com.example.dustdetector.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dustdetector.model.Detector;
import com.example.dustdetector.repository.DetectorRepository;

@Service
public class DetectorService {
    
    @Autowired
    DetectorRepository detectorRepository;

    private static final Logger logger = LoggerFactory.getLogger(DustLevelService.class);

    public List<Detector> findDetectorsByUserId(Integer userId) {

        List<Detector> res = this.detectorRepository.findDetectorsByUserId(userId);
        this.logger.info(String.format("%d detectors gevonden voor gebruiker %d", res.size(), userId));

        return res;
    }
}
