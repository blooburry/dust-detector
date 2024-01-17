package com.example.dustdetector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DustDetectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DustDetectorApplication.class, args);
	}

}
