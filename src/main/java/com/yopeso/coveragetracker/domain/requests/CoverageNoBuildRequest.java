package com.yopeso.coveragetracker.domain.requests;

import lombok.Data;

@Data
public class CoverageNoBuildRequest {

    private final String projectName;
    private final String branchName;
}