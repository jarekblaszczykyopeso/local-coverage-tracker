package com.yopeso.coveragetracker.service;

import com.yopeso.coveragetracker.domain.Measurement;
import com.yopeso.coveragetracker.domain.requests.Branch;
import com.yopeso.coveragetracker.domain.requests.Build;
import com.yopeso.coveragetracker.domain.responses.CoverageResponse;

import java.util.List;
import java.util.Optional;

public interface CoverageService {

    void saveCoverage(Measurement measurement);

    Optional<Integer> getCoverage(Build build);

    Optional<Integer> getLastCoverage(Branch coverageRequest);

    Optional<List<CoverageResponse>> getBranchCoverage(Branch coverageRequest);

    Optional<List<CoverageResponse>> getProjectCoverage(String projectName);
}