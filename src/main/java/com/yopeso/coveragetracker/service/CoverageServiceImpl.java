package com.yopeso.coveragetracker.service;

import com.yopeso.coveragetracker.domain.Coverage;
import com.yopeso.coveragetracker.domain.CoverageRepository;
import com.yopeso.coveragetracker.domain.CoverageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        return coverageRepository.findFirstByCoveragePK_ProjectNameAndCoveragePK_BranchNameAndCoveragePK_BuildNumberOrderByCoveragePK_BuildNumberDesc(coverageRequest.getProjectName(), coverageRequest.getBranchName(), coverageRequest.getBuildNumber()).map(Coverage::getCoverage);
    }
}