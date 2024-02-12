package com.happyfxmas.erdbsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ErdbsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErdbsystemApplication.class, args);
	}

}
