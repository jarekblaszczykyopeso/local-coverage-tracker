package com.yopeso.coveragetracker.service;

import com.yopeso.coveragetracker.domain.Coverage;
import com.yopeso.coveragetracker.domain.CoverageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Implementation of CoverageService.
 */
@Service
public class CoverageServiceImplementation implements CoverageService {
    @Autowired
    CoverageRepository coverageRepository;

    /**
     * Inserts the coverage to the db.
     *
     * @param coverage coverage entity to be inserted.
     */
    @Override
    public void saveCoverage(Coverage coverage) {
        coverageRepository.save(coverage);
    }


    /**
     * Reads the coverage number for the last coverage record in db satisfying the conditions.
     * If date is null, all records for the given project, branch and build are taken into consideration.
     *
     * @param projectName the name of the project
     * @param branchName  the name of the branch in repo
     * @param buildNumber the build number
     * @param date        the date
     * @return Optional<Integer> coverage number or empty optional if no records for given conditions are found
     */

    @Override
    public Optional<Integer> getCoverage(String projectName, String branchName, String buildNumber, LocalDate date) {
        Optional<Coverage> coverage;
        if (date != null) {
            coverage = coverageRepository.findFirstByProjectNameAndBranchNameAndBuildNumberAndDateOrderByIdDesc(projectName, branchName, buildNumber, date);
        } else {
            coverage = coverageRepository.findFirstByProjectNameAndBranchNameAndBuildNumberOrderByIdDesc(projectName, branchName, buildNumber);
        }
        if (coverage.isPresent()) {
            return Optional.of(coverage.get().getCoverage());
        } else {
            return Optional.empty();
        }
    }
}