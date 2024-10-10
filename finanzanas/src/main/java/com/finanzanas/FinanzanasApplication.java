package com.finanzanas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class FinanzanasApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanzanasApplication.class, args);
	}

}
