package com.example.dustdetector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.example.dustdetector.model.User;

import java.util.Collection;

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

    @Modifying
    @Query("INSERT INTO User(u.username, u.password, u.phoneNumber, u.email, u.role, u.twoFAKey) VALUES (?1, ?2, ?3, ?4, ?5, ?6)")
    void createUser(String username, String password, String phoneNumber, String email, int role, String twoFAKey);
}