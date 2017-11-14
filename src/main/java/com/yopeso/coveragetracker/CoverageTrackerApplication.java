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

/**
 * Main class for the application.
 */
@SpringBootApplication
@Log
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
     * @param coverageRepository {@link CoverageRepository} repository
     * @return ApplicationRunner instance
     */
    //TODO delete later the implementation, maybe bean also?
    @Bean
    ApplicationRunner run(CoverageRepository coverageRepository) {
        Stream<String> stream = Stream.of("Jarek", "Adrian", "Daniel");
        return args -> {
            final int[] i = {7};
            stream.forEach(x -> {
                coverageRepository.save(new Coverage(null, x + "Project", x + "Branch", x + "Build", LocalDate.now(), i[0]++));
                coverageRepository.save(new Coverage(null, x + "Project", x + "Branch", x + "Build", LocalDate.now().plusDays(1), i[0]++));
                log.info("coverage now = " + coverageRepository.findFirstByProjectNameAndBranchNameAndBuildNumberAndDateOrderByIdDesc(x + "Project", x + "Branch", x + "Build", LocalDate.now()).get().getCoverage());
                log.info("coverage tomorrow = " + coverageRepository.findFirstByProjectNameAndBranchNameAndBuildNumberAndDateOrderByIdDesc(x + "Project", x + "Branch", x + "Build", LocalDate.now().plusDays(1)).get().getCoverage());
                log.info("coverage no date = " + coverageRepository.findFirstByProjectNameAndBranchNameAndBuildNumberOrderByIdDesc(x + "Project", x + "Branch", x + "Build").get().getCoverage());
            });
        };
    }
}