package com.yopeso.coveragetracker.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoverageRepository extends JpaRepository<Coverage, Long> {

    Optional<Coverage> findFirstByProjectNameAndBranchNameAndBuildNumberOrderByIdDesc(String projectName, String branchName, String buildNumber);
}