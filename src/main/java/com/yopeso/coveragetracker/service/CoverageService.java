package com.yopeso.coveragetracker.service;

import com.yopeso.coveragetracker.domain.Coverage;

import java.time.LocalDate;

/**
 * Service for coverage.
 */


public interface CoverageService {
    void saveCoverage(Coverage coverage);
    int getCoverage(String projectName, String projectBranch, String buildNumber, LocalDate date);
}