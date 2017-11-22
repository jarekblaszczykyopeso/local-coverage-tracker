package com.yopeso.coveragetracker.domain.requests;

import lombok.Data;

@Data
public class BuildRequest {

    private final String projectName;
    private final String branchName;
    private final int buildNumber;
}