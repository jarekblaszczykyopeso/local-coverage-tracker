package com.yopeso.coveragetracker.domain.responses;

import com.yopeso.coveragetracker.domain.Coverage;
import lombok.Getter;

@Getter
public class CoverageResponse {
    private final String project;
    private final String branch;
    private final int build;
    private final int coverage;

    public CoverageResponse(Coverage coverageEntity) {
        project = coverageEntity.getCoveragePK().getProjectName();
        branch = coverageEntity.getCoveragePK().getBranchName();
        build = coverageEntity.getCoveragePK().getBuildNumber();
        coverage = coverageEntity.getCoverage();

    }
}


