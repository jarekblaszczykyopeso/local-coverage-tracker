package com.yopeso.coveragetracker.domain;

import lombok.Data;

import java.time.LocalDate;

/**
 * Coverage entity for request.
 */
@Data

public class CoverageRequest {

    private final String projectName;
    private final String branchName;
    private final String buildNumber;
    private final LocalDate date;
}