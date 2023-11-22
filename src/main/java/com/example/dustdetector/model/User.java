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
   @Column(name = "id")
   private int id;

   @Column(name = "Gebruikersnaam", nullable = false)
   private String username;

   @Column(name = "Wachtwoord", nullable = false)
   private String password;

   @Column(name = "Telefoonnummer")
   private String phoneNumber;

   @Column(name = "Emailadres")
   private String email;

   @Column(name = "Rol", nullable = false)
   private int role;

   @Column(name = "2FAKey")
   private String twoFAKey;
}
