package com.example.dustdetector.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

import com.example.dustdetector.service.DatabaseUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private DatabaseUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
        daoAuthProvider.setPasswordEncoder(passwordEncoder());
        daoAuthProvider.setUserDetailsService(this.userDetailsService);
        ProviderManager providerManager = new ProviderManager(daoAuthProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

    @Bean // Security configuratie voor webpagina's en web API
    @Order(1)
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http, 
        CustomAuthenticationHandler successHandler, 
        CustomAuthenticationHandler failureHandler, 
        CustomLogoutHandler logoutHandler
    ) throws Exception {
        http
        .csrf(c -> c.disable())
        .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                            "/dashboard",
                            "/webshop",
                            "/api/detectors"
                        ).authenticated() // beveiligde paginas
                        .requestMatchers(
                            "/home", 
                            "/medische-informatie",
                            "/signin", 
                            "/api/ping",
                            "/signup",
                            "/api/signup",
                            "/api/meting",
                            "/medische-informatie",
                            "/onze-organisatie",
                            "/support",
                            "/ws/**",
                            "/"
                        ).permitAll() // openbare paginas
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll())
                .formLogin((form) -> form
                        .loginPage("/signin")
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                        .permitAll())
                .logout((logout) -> logout
                    .logoutSuccessHandler(logoutHandler)
                    .permitAll());

        return http.build();
    }
}
