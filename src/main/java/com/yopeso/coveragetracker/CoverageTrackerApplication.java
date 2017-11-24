package com.yopeso.coveragetracker;

import com.yopeso.coveragetracker.domain.CoveragePK;
import com.yopeso.coveragetracker.domain.CoverageRepository;
import com.yopeso.coveragetracker.domain.Measurement;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
        return (ApplicationArguments args) -> {
            final int[] i = {7};
            final int[] build = {1};
            stream.forEach((String x) -> {
                final String project = "Project";
                final String branch = "Branch";
                coverageRepository.save(new Measurement(i[0]++, new CoveragePK(x + project, x + branch, build[0]++)));
                coverageRepository.save(new Measurement(i[0]++, new CoveragePK(x + project, x + branch, build[0]++)));
            });
        };
    }

}