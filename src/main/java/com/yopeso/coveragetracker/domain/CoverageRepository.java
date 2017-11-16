package com.yopeso.coveragetracker.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoverageRepository extends JpaRepository<Coverage, CoveragePK> {

    Optional<Coverage> findFirstByCoveragePK_ProjectNameAndCoveragePK_BranchNameAndCoveragePK_BuildNumberOrderByCoveragePK_BuildNumberDesc(String projectName, String branchName, int buildNumber);
}