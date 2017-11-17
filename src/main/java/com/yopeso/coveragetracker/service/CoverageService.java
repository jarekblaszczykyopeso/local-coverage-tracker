package com.yopeso.coveragetracker.service;

import com.yopeso.coveragetracker.domain.Coverage;
import com.yopeso.coveragetracker.domain.requests.CoverageNoBuildRequest;
import com.yopeso.coveragetracker.domain.requests.CoverageRequest;
import com.yopeso.coveragetracker.domain.responses.CoverageResponse;

import java.util.List;
import java.util.Optional;

public interface CoverageService {

    void saveCoverage(Coverage coverage);

    Optional<Integer> getCoverage(CoverageRequest coverageRequest);

    Optional<Integer> getLastCoverage(CoverageNoBuildRequest coverageRequest);

    List<CoverageResponse> getBranchCoverage(CoverageNoBuildRequest coverageRequest);
}