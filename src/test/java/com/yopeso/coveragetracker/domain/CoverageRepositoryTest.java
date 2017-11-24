package com.yopeso.coveragetracker.domain;

import com.yopeso.coveragetracker.CoverageTrackerApplication;
import org.junit.After;
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
    private CoverageRepository coverageRepository;

    private final String project = "project";
    private final String branch = "branch";
    private final String badBranch = "badBranch";
    private final int buildNumber11 = 11;
    private final int coverage11 = 11;
    private final int buildNumber12 = 12;
    private final int coverage12 = 12;
    private final String branch21 = "branch21";
    private final int buildNumber21 = 21;
    private final int coverage21 = 21;

    @Test
    public void testSaveCoverage() {
        final Measurement measurement11 = new Measurement(coverage11, new CoveragePK(project, branch, buildNumber11));
        coverageRepository.save(measurement11);
        final Measurement measurement12 = new Measurement(coverage12, new CoveragePK(project, branch, buildNumber12));
        coverageRepository.save(measurement12);
        final Measurement measurement21 = new Measurement(coverage21, new CoveragePK(project, branch21, buildNumber21));
        coverageRepository.save(measurement21);
        coverageRepository.deleteAll();
    }

    @Test
    public void testGetCoverage() {
        auxSaveMeasurements();
        final Optional<Measurement> optionalCoverage11 = coverageRepository.findByCoveragePK_ProjectNameAndCoveragePK_BranchNameAndCoveragePK_BuildNumber(project, branch, buildNumber11);
        assertTrue(optionalCoverage11.isPresent());
        assertEquals(coverage11, optionalCoverage11.orElseThrow(RuntimeException::new).getCoverage());
        auxClearMeasurements();
    }

    @Test
    public void testGetCoverageBad() {
        auxSaveMeasurements();
        final Optional<Measurement> optionalCoverageBad = coverageRepository.findByCoveragePK_ProjectNameAndCoveragePK_BranchNameAndCoveragePK_BuildNumber(project, badBranch, buildNumber11);
        assertFalse(optionalCoverageBad.isPresent());
        auxClearMeasurements();
    }


    @Test
    public void testGetLastCoverage() {
        auxSaveMeasurements();
        final Optional<Measurement> optionalLastCoverage = coverageRepository.findFirstByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberDesc(project, branch);
        assertTrue(optionalLastCoverage.isPresent());
        assertEquals(coverage12, optionalLastCoverage.orElseThrow(RuntimeException::new).getCoverage());
        auxClearMeasurements();
    }

    @Test
    public void testGetBranchCoverage() {
        auxSaveMeasurements();
        final Optional<List<Measurement>> branchCoverage = coverageRepository.findByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberAsc(project, branch);
        assertTrue(branchCoverage.isPresent());
        assertEquals(coverage11, branchCoverage.get().get(0).getCoverage());
        auxClearMeasurements();
    }

    @Test
    public void testGetProjectCoverage() {
        auxSaveMeasurements();
        final Optional<List<Measurement>> projectCoverage = coverageRepository.findByCoveragePK_ProjectNameOrderByCoveragePK_BranchNameAscCoveragePK_BuildNumberAsc(project);
        assertTrue(projectCoverage.isPresent());
        assertEquals(coverage21, projectCoverage.get().get(2).getCoverage());
        auxClearMeasurements();
    }

    private void auxSaveMeasurements() {
        final Measurement measurement11 = new Measurement(coverage11, new CoveragePK(project, branch, buildNumber11));
        coverageRepository.save(measurement11);
        final Measurement measurement12 = new Measurement(coverage12, new CoveragePK(project, branch, buildNumber12));
        coverageRepository.save(measurement12);
        final Measurement measurement21 = new Measurement(coverage21, new CoveragePK(project, branch21, buildNumber21));
        coverageRepository.save(measurement21);

    }

    @After
    public void clearData() {
        auxClearMeasurements();
    }

    public void auxClearMeasurements() {
        coverageRepository.deleteAll();
    }


}