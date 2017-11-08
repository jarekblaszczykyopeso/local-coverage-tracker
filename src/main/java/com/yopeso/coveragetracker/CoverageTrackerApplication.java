package com.yopeso.coveragetracker;

import com.yopeso.coveragetracker.domain.Coverage;
import com.yopeso.coveragetracker.service.CoverageService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.stream.Stream;

/**
 * Main class for the application.
 */
@SpringBootApplication
public class CoverageTrackerApplication {

    /**
     * Main method.
     *
     * @param args String[] app parameters
     */
    public static void main(String[] args) {
        SpringApplication.run(CoverageTrackerApplication.class, args);
    }

    /**
     * Bean executed when app is starting.
     *
     * @param cs CoverageService service
     * @return ApplicationRunner instance
     */
    //TODO delete later the implementation, maybe bean also?
    @Bean
    ApplicationRunner run(CoverageService cs) {
        Stream<String> stream = Stream.of("Jarek", "Adrian", "Daniel");
        return args -> {
            final int[] i = {7};
            stream.forEach(x -> {
                cs.saveCoverage(new Coverage(null, x + "Project", x + "Branch", x + "Build", LocalDate.now(), i[0]++));
                cs.saveCoverage(new Coverage(null, x + "Project", x + "Branch", x + "Build", LocalDate.now().plusDays(1), i[0]++));
                System.out.println("coverage now = " + cs.getCoverage(x + "Project", x + "Branch", x + "Build", LocalDate.now()));
                System.out.println("coverage tomorrow = " + cs.getCoverage(x + "Project", x + "Branch", x + "Build", LocalDate.now().plusDays(1)));
                System.out.println("coverage null = " + cs.getCoverage(x + "Project", x + "Branch", x + "Build", null));
            });
        };
    }
}