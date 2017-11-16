package com.yopeso.coveragetracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
public class Coverage {
    @EmbeddedId
    private CoveragePK coveragePK;
    private LocalDate date;
    private int coverage;

    public Coverage(String projectName, String branchName, String buildNumber, LocalDate date, int coverage) {
        this.coveragePK = new CoveragePK(projectName, branchName, buildNumber);
        this.date = date;
        this.coverage = coverage;
    }
}