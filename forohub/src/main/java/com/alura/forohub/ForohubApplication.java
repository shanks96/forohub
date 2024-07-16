package com.alura.forohub;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ForohubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForohubApplication.class, args);
	}
}