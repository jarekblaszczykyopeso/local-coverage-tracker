package com.yopeso.coveragetracker.service;

import com.yopeso.coveragetracker.domain.Coverage;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Service for coverage.
 */


public interface CoverageService {
    void saveCoverage(Coverage coverage);

    Optional<Integer> getCoverage(String projectName, String projectBranch, String buildNumber, LocalDate date);
}