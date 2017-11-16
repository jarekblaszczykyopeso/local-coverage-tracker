package com.yopeso.coveragetracker.domain;

import lombok.Data;

@Data
public class CoverageRequest {

    private final String projectName;
    private final String branchName;
    private final int buildNumber;
}