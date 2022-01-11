package com.barauna.DEVinHouse.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeansConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FlywayMigrationStrategy getFlyway() {
        return new FlywayMigrationStrategy(){
            @Override
            public void migrate(Flyway flyway) {
                System.out.println("I' here");
            }
        };
    }
}
