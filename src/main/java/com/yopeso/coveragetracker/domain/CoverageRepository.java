package com.yopeso.coveragetracker.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository for handle db operations for coverage.
 */
public interface CoverageRepository extends JpaRepository<Coverage, Long> {
    Optional<Coverage> findFirstByProjectNameAndBranchNameAndBuildNumberAndDateOrderByIdDesc(
            String projectName, String branchName, String buildNumber, LocalDate date);

    Optional<Coverage> findFirstByProjectNameAndBranchNameAndBuildNumberOrderByIdDesc(String projectName, String branchName, String buildNumber);
}