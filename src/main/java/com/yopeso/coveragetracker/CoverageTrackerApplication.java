package com.yopeso.coveragetracker;

import com.yopeso.coveragetracker.domain.Coverage;
import com.yopeso.coveragetracker.domain.CoverageRepository;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.stream.Stream;

@SpringBootApplication
@Log
public class CoverageTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoverageTrackerApplication.class, args);
    }

    @Bean
    ApplicationRunner run(CoverageRepository coverageRepository) {
        final String jarek = "Jarek";
        final String adrian = "Adrian";
        final String daniel = "Daniel";
        Stream<String> stream = Stream.of(jarek, adrian, daniel);
        return args -> {
            final int[] i = {7};
            stream.forEach(x -> {
                final String project = "Project";
                final String branch = "Branch";
                final String build = "Build";
                coverageRepository.save(new Coverage(null, x + project, x + branch, x + build, LocalDate.now(), i[0]++));
                coverageRepository.save(new Coverage(null, x + project, x + branch, x + build, LocalDate.now().plusDays(1), i[0]++));
            });
        };
    }
}