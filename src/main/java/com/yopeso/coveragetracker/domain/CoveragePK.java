package com.yopeso.coveragetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class CoveragePK implements Serializable {
    private String projectName;
    private String branchName;
    private int buildNumber;
}
