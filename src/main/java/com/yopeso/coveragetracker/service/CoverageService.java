package com.yopeso.coveragetracker.service;

import com.yopeso.coveragetracker.domain.Coverage;
import com.yopeso.coveragetracker.domain.requests.BranchRequest;
import com.yopeso.coveragetracker.domain.requests.BuildRequest;
import com.yopeso.coveragetracker.domain.responses.CoverageResponse;

import java.util.List;
import java.util.Optional;

public interface CoverageService {

    void saveCoverage(Coverage coverage);

    Optional<Integer> getCoverage(BuildRequest buildRequest);

    Optional<Integer> getLastCoverage(BranchRequest coverageRequest);

    Optional<List<CoverageResponse>> getBranchCoverage(BranchRequest coverageRequest);

    Optional<List<CoverageResponse>> getProjectCoverage(String projectName);
}