package com.example.dustdetector.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DustLevel")
@Data
@AllArgsConstructor
public class DustLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "detectorId")
    private int detectorId;

    @Column(name = "level")
    private int level;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "detectorId", referencedColumnName = "id", insertable = false, updatable = false)
    private Detector detector;

    // Constructor requiring all three fields
    public DustLevel(int detectorId, int level) {
        this.detectorId = detectorId;
        this.level = level;
        this.date = new Date();
    }
}