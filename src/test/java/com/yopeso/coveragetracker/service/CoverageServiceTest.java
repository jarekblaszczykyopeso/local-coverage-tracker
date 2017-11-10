package com.yopeso.coveragetracker.service;

import com.yopeso.coveragetracker.CoverageTrackerApplication;
import com.yopeso.coveragetracker.domain.Coverage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests for coverage service.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoverageTrackerApplication.class)
public class CoverageServiceTest {
    @Autowired
    CoverageService coverageService;

    Coverage april27 = new Coverage(null, "project", "branch", "build", LocalDate.of(2017, 4, 27), 7);
    Coverage april28 = new Coverage(null, "project", "branch", "build", LocalDate.of(2017, 4, 28), 8);

    /**
     * Testing the 4 cases: find coverage by date, find without date, try to find not existing record (date and then project name).
     */
    @Test
    public void testService() {
        coverageService.saveCoverage(april27);
        coverageService.saveCoverage(april28);
        Optional<Integer> coverageDate = coverageService.getCoverage("project", "branch", "build", LocalDate.of(2017, 4, 27));
        Optional<Integer> coverageNoDate = coverageService.getCoverage("project", "branch", "build", null);
        Optional<Integer> coverageBadDate = coverageService.getCoverage("project", "branch", "build", LocalDate.of(2017, 4, 29));
        Optional<Integer> coverageBadProjectNoDate = coverageService.getCoverage("projectVeryBad", "branch", "build", null);
        assertEquals(7, coverageDate.get().intValue());
        assertEquals(8, coverageNoDate.get().intValue());
        assertFalse(coverageBadDate.isPresent());
        assertFalse(coverageBadProjectNoDate.isPresent());
    }
}