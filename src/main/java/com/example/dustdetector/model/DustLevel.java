package com.example.dustdetector.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DustLevel")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(DustLevelId.class)
public class DustLevel {

    @Id
    @ManyToOne
    @JoinColumn(name = "detectorId")
    private Detector detector;

    @Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "level", nullable = false)
    private int level;

    // Constructor requiring the two id fields
    public DustLevel(Detector detector, int level) {
        this.detector = detector;
        this.level = level;
        this.date = new Date();
    }
}

@Data
class DustLevelId implements Serializable {
    
    private Detector detector;

    private Date date;
}