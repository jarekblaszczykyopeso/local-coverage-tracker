package com.yopeso.coveragetracker.domain.responses;

import com.yopeso.coveragetracker.domain.Measurement;
import lombok.Getter;

@Getter
public class CoverageResponse {
    private final String project;
    private final String branch;
    private final int build;
    private final int coverage;

    public CoverageResponse(Measurement measurementEntity) {
        project = measurementEntity.getCoveragePK().getProjectName();
        branch = measurementEntity.getCoveragePK().getBranchName();
        build = measurementEntity.getCoveragePK().getBuildNumber();
        coverage = measurementEntity.getCoverage();

    }
}


