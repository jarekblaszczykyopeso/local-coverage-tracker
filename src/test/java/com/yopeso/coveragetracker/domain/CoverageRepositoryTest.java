package com.yopeso.coveragetracker.domain;

import com.yopeso.coveragetracker.CoverageTrackerApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Before
    public void setUp() throws Exception {
        final Coverage cov11 = new Coverage(project, branch, buildNumber11, coverage11);
        coverageRepository.save(cov11);
        final Coverage cov12 = new Coverage(project, branch, buildNumber12, coverage12);
        coverageRepository.save(cov12);
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
    public void testGetLastoverage() {
        final Optional<Coverage> optionalLastCoverage = coverageRepository.findFirstByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberDesc(project, branch);
        assertTrue(optionalLastCoverage.isPresent());
        assertEquals(coverage12, optionalLastCoverage.orElseThrow(RuntimeException::new).getCoverage());
    }
}