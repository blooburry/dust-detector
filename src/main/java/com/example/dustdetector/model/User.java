package com.example.dustdetector.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "User")
@Data
@AllArgsConstructor
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @Column(nullable = false)
   private String username;

   @Column(nullable = false)
   private String password;

   @Column
   private String phone;

   @Column
   private String email;

   @Column(nullable = false)
   private int role;

   @Column
   private String verificationKey;
}
