package com.yopeso.coveragetracker.service;

import com.yopeso.coveragetracker.domain.Coverage;
import com.yopeso.coveragetracker.domain.CoverageRequest;

import java.util.Optional;

public interface CoverageService {

    void saveCoverage(Coverage coverage);

    Optional<Integer> getCoverage(CoverageRequest coverageRequest);
}