package com.example.dustdetector.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dustdetector.controller.PingController;
import com.example.dustdetector.model.User;
import com.example.dustdetector.repository.UserRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    // Dependency needed because this service needs to check credentials in the
    // database
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(PingController.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        this.logger.info("De database wordt gequeried voor een gebruiker met gebruikersnaam: " + username);
        
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new UserDetailsPrincipal(user);
    }

}

class UserDetailsPrincipal implements UserDetails {
    private User user;

    public UserDetailsPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        switch (this.user.getRole()) {
            case 0: // Assuming 0 means Admin
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                break;
            case 1: // Assuming 1 means User
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                break;
            default:
                throw new IllegalArgumentException("Invalid role value");
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}