package com.yopeso.coveragetracker.domain.requests;

import lombok.Data;

@Data
public class Branch {

    private final String projectName;
    private final String branchName;
}