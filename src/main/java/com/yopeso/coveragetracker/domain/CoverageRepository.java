package com.yopeso.coveragetracker.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoverageRepository extends JpaRepository<Coverage, CoveragePK> {

    Optional<Coverage> findByCoveragePK_ProjectNameAndCoveragePK_BranchNameAndCoveragePK_BuildNumber(String projectName, String branchName, int buildNumber);

    Optional<Coverage> findFirstByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberDesc(String projectName, String branchName);

    List<Coverage> findByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberAsc(String projectName, String branchName);

}