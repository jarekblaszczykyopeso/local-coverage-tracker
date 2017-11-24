package com.yopeso.coveragetracker.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoverageRepository extends JpaRepository<Measurement, CoveragePK> {

    Optional<Measurement> findByCoveragePK_ProjectNameAndCoveragePK_BranchNameAndCoveragePK_BuildNumber(String projectName, String branchName, int buildNumber);

    Optional<Measurement> findFirstByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberDesc(String projectName, String branchName);

    Optional<List<Measurement>> findByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberAsc(String projectName, String branchName);

    Optional<List<Measurement>> findByCoveragePK_ProjectNameOrderByCoveragePK_BranchNameAscCoveragePK_BuildNumberAsc(String projectName);

}