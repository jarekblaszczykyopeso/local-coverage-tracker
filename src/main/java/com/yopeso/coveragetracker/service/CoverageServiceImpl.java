package com.yopeso.coveragetracker.service;

import com.yopeso.coveragetracker.domain.Coverage;
import com.yopeso.coveragetracker.domain.CoverageRepository;
import com.yopeso.coveragetracker.domain.requests.BranchRequest;
import com.yopeso.coveragetracker.domain.requests.BuildRequest;
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
    public void saveCoverage(Coverage coverage) {
        coverageRepository.save(coverage);
    }

    @Override
    public Optional<Integer> getCoverage(BuildRequest buildRequest) {
        return coverageRepository.findByCoveragePK_ProjectNameAndCoveragePK_BranchNameAndCoveragePK_BuildNumber(buildRequest.getProjectName(), buildRequest.getBranchName(), buildRequest.getBuildNumber()).map(Coverage::getCoverage);
    }

    @Override
    public Optional<Integer> getLastCoverage(BranchRequest coverageRequest) {
        return coverageRepository.findFirstByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberDesc(coverageRequest.getProjectName(), coverageRequest.getBranchName()).map(Coverage::getCoverage);
    }

    @Override
    public Optional<List<CoverageResponse>> getBranchCoverage(BranchRequest coverageRequest) {
        Optional<List<Coverage>> coverage = coverageRepository.findByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberAsc(coverageRequest.getProjectName(), coverageRequest.getBranchName());
        if (coverage.isPresent()) {
            return Optional.of(coverage.get().stream().map(x -> new CoverageResponse(x)).collect(Collectors.toList()));
        } else { //no coverage found
            return Optional.empty();
        }

    }

    @Override
    public Optional<List<CoverageResponse>> getProjectCoverage(String projectName) {
        Optional<List<Coverage>> coverage = coverageRepository.findByCoveragePK_ProjectNameOrderByCoveragePK_BranchNameAscCoveragePK_BuildNumberAsc(projectName);
        if (coverage.isPresent()) {
            return Optional.of(coverage.get().stream().map(x -> new CoverageResponse(x)).collect(Collectors.toList()));
        } else {//no coverage found
            return Optional.empty();
        }
    }
}