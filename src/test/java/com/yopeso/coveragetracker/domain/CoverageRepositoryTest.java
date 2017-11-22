package com.yopeso.coveragetracker.domain;

import com.yopeso.coveragetracker.CoverageTrackerApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoverageTrackerApplication.class)
public class CoverageRepositoryTest {

    @Autowired
    CoverageRepository coverageRepository;

    final String project = "project";
    final String branch = "branch";
    final String badBranch = "badBranch";
    final int buildNumber11 = 11;
    final int coverage11 = 11;
    final int buildNumber12 = 12;
    final int coverage12 = 12;
    final String branch21 = "branch21";
    final int buildNumber21 = 21;
    final int coverage21 = 21;

    @Before
    public void setUp() throws Exception {
        final Coverage cov11 = new Coverage(project, branch, buildNumber11, coverage11);
        coverageRepository.save(cov11);
        final Coverage cov12 = new Coverage(project, branch, buildNumber12, coverage12);
        coverageRepository.save(cov12);
        final Coverage cov21 = new Coverage(project, branch21, buildNumber21, coverage21);
        coverageRepository.save(cov21);
    }

    @Test
    public void testGetCoverage() {
        final Optional<Coverage> optionalCoverage11 = coverageRepository.findByCoveragePK_ProjectNameAndCoveragePK_BranchNameAndCoveragePK_BuildNumber(project, branch, buildNumber11);
        assertTrue(optionalCoverage11.isPresent());
        assertEquals(coverage11, optionalCoverage11.orElseThrow(RuntimeException::new).getCoverage());

    }

    @Test
    public void testGetCoverageBad() {
        final Optional<Coverage> optionalCoverageBad = coverageRepository.findByCoveragePK_ProjectNameAndCoveragePK_BranchNameAndCoveragePK_BuildNumber(project, badBranch, buildNumber11);
        assertFalse(optionalCoverageBad.isPresent());
    }


    @Test
    public void testGetLastCoverage() {
        final Optional<Coverage> optionalLastCoverage = coverageRepository.findFirstByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberDesc(project, branch);
        assertTrue(optionalLastCoverage.isPresent());
        assertEquals(coverage12, optionalLastCoverage.orElseThrow(RuntimeException::new).getCoverage());
    }

    @Test
    public void testGetBranchCoverage() {
        final Optional<List<Coverage>> branchCoverage = coverageRepository.findByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberAsc(project, branch);
        assertTrue(branchCoverage.isPresent());
        assertEquals(coverage11, branchCoverage.get().get(0).getCoverage());
    }

    @Test
    public void testGetProjectCoverage() {
        final Optional<List<Coverage>> projectCoverage = coverageRepository.findByCoveragePK_ProjectNameOrderByCoveragePK_BranchNameAscCoveragePK_BuildNumberAsc(project);
        assertTrue(projectCoverage.isPresent());
        assertEquals(coverage21, projectCoverage.get().get(2).getCoverage());
    }


}