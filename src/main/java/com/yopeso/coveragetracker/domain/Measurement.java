package com.yopeso.coveragetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Measurement {
    @Min(0)
    @Max(100)
    private int coverage;
    @Valid
    @EmbeddedId
    private CoveragePK coveragePK;
}