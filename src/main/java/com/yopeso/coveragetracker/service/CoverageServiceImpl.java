package com.yopeso.coveragetracker.service;

import com.yopeso.coveragetracker.domain.CoverageRepository;
import com.yopeso.coveragetracker.domain.Measurement;
import com.yopeso.coveragetracker.domain.requests.Branch;
import com.yopeso.coveragetracker.domain.requests.Build;
import com.yopeso.coveragetracker.domain.responses.CoverageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoverageServiceImpl implements CoverageService {

    private final CoverageRepository coverageRepository;

    @Override
    public void saveCoverage(Measurement measurement) {
        coverageRepository.save(measurement);
    }

    @Override
    public Optional<Integer> getCoverage(Build build) {
        return coverageRepository.findByCoveragePK_ProjectNameAndCoveragePK_BranchNameAndCoveragePK_BuildNumber(build.getProjectName(), build.getBranchName(), build.getBuildNumber()).map(Measurement::getCoverage);
    }

    @Override
    public Optional<Integer> getLastCoverage(Branch coverageRequest) {
        return coverageRepository.findFirstByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberDesc(coverageRequest.getProjectName(), coverageRequest.getBranchName()).map(Measurement::getCoverage);
    }

    @Override
    public Optional<List<CoverageResponse>> getBranchCoverage(Branch coverageRequest) {
        Optional<List<Measurement>> coverage = coverageRepository.findByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberAsc(coverageRequest.getProjectName(), coverageRequest.getBranchName());
        return coverage.map(measurements -> measurements.stream().map(CoverageResponse::new).collect(Collectors.toList()));
    }

    @Override
    public Optional<List<CoverageResponse>> getProjectCoverage(String projectName) {
        Optional<List<Measurement>> coverage = coverageRepository.findByCoveragePK_ProjectNameOrderByCoveragePK_BranchNameAscCoveragePK_BuildNumberAsc(projectName);
        return coverage.map(measurements -> measurements.stream().map(CoverageResponse::new).collect(Collectors.toList()));
    }
}