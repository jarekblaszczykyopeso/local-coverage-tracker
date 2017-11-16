package com.yopeso.coveragetracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Data
public class Coverage {
    @EmbeddedId
    private CoveragePK coveragePK;
    private int coverage;

    public Coverage(String projectName, String branchName, int buildNumber, int coverage) {
        this.coveragePK = new CoveragePK(projectName, branchName, buildNumber);
        this.coverage = coverage;
    }
}