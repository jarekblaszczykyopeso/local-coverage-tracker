package com.yopeso.coveragetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * Coverage entity.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Coverage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String projectName;
    private String branchName;
    private String buildNumber;
    private LocalDate date;
    private int coverage;
}