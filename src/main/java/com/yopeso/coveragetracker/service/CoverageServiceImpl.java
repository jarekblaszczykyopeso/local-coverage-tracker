package com.yopeso.coveragetracker.service;

import com.yopeso.coveragetracker.domain.Coverage;
import com.yopeso.coveragetracker.domain.CoverageRepository;
import com.yopeso.coveragetracker.domain.requests.CoverageNoBuildRequest;
import com.yopeso.coveragetracker.domain.requests.CoverageRequest;
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
    public Optional<Integer> getCoverage(CoverageRequest coverageRequest) {
        return coverageRepository.findByCoveragePK_ProjectNameAndCoveragePK_BranchNameAndCoveragePK_BuildNumber(coverageRequest.getProjectName(), coverageRequest.getBranchName(), coverageRequest.getBuildNumber()).map(Coverage::getCoverage);
    }

    @Override
    public Optional<Integer> getLastCoverage(CoverageNoBuildRequest coverageRequest) {
        return coverageRepository.findFirstByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberDesc(coverageRequest.getProjectName(), coverageRequest.getBranchName()).map(Coverage::getCoverage);
    }

    @Override
    public List<CoverageResponse> getBranchCoverage(CoverageNoBuildRequest coverageRequest) {
        return coverageRepository.findByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberAsc(coverageRequest.getProjectName(), coverageRequest.getBranchName()).stream().map(
                x -> new CoverageResponse(x)).collect(Collectors.toList());
    }

    @Override
    public List<CoverageResponse> getProjectCoverage(String projectName) {
        return coverageRepository.findByCoveragePK_ProjectNameOrderByCoveragePK_BranchNameAscCoveragePK_BuildNumberAsc(projectName).stream().map(
                x -> new CoverageResponse(x)).collect(Collectors.toList());
    }
}