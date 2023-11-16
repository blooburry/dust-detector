package com.example.dustdetector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Collection;

@Entity
@Table(name = "User")
class User {

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

   // Getters and Setters
   public int getId() {
       return id;
   }

   public void setId(int id) {
       this.id = id;
   }

   public String getUsername() {
       return username;
   }

   public void setUsername(String username) {
       this.username = username;
   }

   public String getPassword() {
       return password;
   }

   public void setPassword(String password) {
       this.password = password;
   }

   public String getPhoneNumber() {
       return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
       this.phoneNumber = phoneNumber;
   }

   public String getEmail() {
       return email;
   }

   public void setEmail(String email) {
       this.email = email;
   }

   public int getRole() {
       return role;
   }

   public void setRole(int role) {
       this.role = role;
   }

   public String getTwoFAKey() {
       return twoFAKey;
   }

   public void setTwoFAKey(String twoFAKey) {
       this.twoFAKey = twoFAKey;
   }
}

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.role = ?1")
    Collection<User> findUsersByRole(Integer role);
  
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findUserByEmail(String email);
  
    @Modifying
    @Query("UPDATE User u SET u.password = ?1 WHERE u.id = ?2")
    void updateUserPassword(String password, Integer id);
  
    @Modifying
    @Query("DELETE FROM User u WHERE u.id = ?1")
    void deleteUserById(Integer id);
}