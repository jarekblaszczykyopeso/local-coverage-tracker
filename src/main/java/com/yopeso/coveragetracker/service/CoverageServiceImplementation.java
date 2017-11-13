package com.yopeso.coveragetracker.service;

import com.yopeso.coveragetracker.domain.Coverage;
import com.yopeso.coveragetracker.domain.CoverageRepository;
import com.yopeso.coveragetracker.domain.CoverageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @param coverageRequest CoverageRequest entity with project, branch, build and date
     * @return Optional<Integer> coverage number or empty optional if no records for given conditions are found
     */

    @Override
    public Optional<Integer> getCoverage(CoverageRequest coverageRequest) {
        Optional<Coverage> coverage;
        if (coverageRequest.getDate() != null) {
            coverage = coverageRepository.findFirstByProjectNameAndBranchNameAndBuildNumberAndDateOrderByIdDesc(coverageRequest.getProjectName(), coverageRequest.getBranchName(), coverageRequest.getBuildNumber(), coverageRequest.getDate());
        } else {
            coverage = coverageRepository.findFirstByProjectNameAndBranchNameAndBuildNumberOrderByIdDesc(coverageRequest.getProjectName(), coverageRequest.getBranchName(), coverageRequest.getBuildNumber());
        }
        if (coverage.isPresent()) {
            return Optional.of(coverage.get().getCoverage());
        } else {
            return Optional.empty();
        }
    }
}