package com.example.dustdetector.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "Detector")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Detector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ip_address", unique = true, nullable = false)
    private String ipAddress;

    @Column(name = "dateBought", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateBought;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
}