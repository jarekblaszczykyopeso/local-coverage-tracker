package com.yopeso.coveragetracker.domain;

import com.yopeso.coveragetracker.CoverageTrackerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoverageTrackerApplication.class)
public class CoverageRepositoryTest {

    @Autowired
    CoverageRepository coverageRepository;

    @Test
    public void testRepo() {
        final String project = "project";
        final String branch = "branch";
        final Coverage cov1 = new Coverage(project, branch, 11, 7);
        coverageRepository.save(cov1);
        Optional<Coverage> optionalCoverage = coverageRepository.findFirstByCoveragePK_ProjectNameAndCoveragePK_BranchNameAndCoveragePK_BuildNumberOrderByCoveragePK_BuildNumberDesc(project, branch, 11);
        assertTrue(optionalCoverage.isPresent());
        assertEquals(7, optionalCoverage.orElseThrow(RuntimeException::new).getCoverage());
    }
}