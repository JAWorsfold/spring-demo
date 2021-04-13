package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student arthur = new Student(
                    "Arthur",
                    "arthur.king.of.the.britons@gmail.com",
                    LocalDate.of(1970, JANUARY, 1),
                    50
            );
            Student robin = new Student(
                    "Robin",
                    "robin.ran.away@gmail.com",
                    LocalDate.of(1980, JANUARY, 1),
                    40
            );

            repository.saveAll(
                    List.of(arthur, robin)
            );
        };
    }

}
