package com.alura.forohub;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ForohubApplication_Bk2 {

	public static void main(String[] args) {
		SpringApplication.run(ForohubApplication_Bk2.class, args);
	}

	@Bean
	public FlywayMigrationStrategy flywayMigrationStrategy(){
		return new migrationStrategy();
	}

	private static class migrationStrategy implements FlywayMigrationStrategy{
		@Override
		public void migrate(Flyway flyway){
			flyway.migrate();
		}
	}
}
