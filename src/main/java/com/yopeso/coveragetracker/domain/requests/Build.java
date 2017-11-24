package com.yopeso.coveragetracker.domain.requests;

import lombok.Data;

@Data
public class Build {

    private final String projectName;
    private final String branchName;
    private final int buildNumber;
}