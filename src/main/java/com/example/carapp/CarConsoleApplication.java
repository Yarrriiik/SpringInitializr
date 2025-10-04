package com.example.carapp;

import com.example.carapp.console.ConsoleUI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CarConsoleApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarConsoleApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ConsoleUI ui) {
        return args -> ui.run();
    }
}
