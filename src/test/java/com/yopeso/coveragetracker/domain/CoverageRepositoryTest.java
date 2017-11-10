package com.yopeso.coveragetracker.domain;

import com.yopeso.coveragetracker.CoverageTrackerApplication;
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
 * Test for repository.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoverageTrackerApplication.class)
public class CoverageRepositoryTest {
    @Autowired
    CoverageRepository coverageRepository;

    Coverage april27 = new Coverage(null, "project", "branch", "build", LocalDate.of(2017, 4, 27), 7);
    Coverage april28 = new Coverage(null, "project", "branch", "build", LocalDate.of(2017, 4, 28), 8);

    /**
     * Testing the 4 cases: find coverage by date, find without date, try to find not existing record (date and then project name).
     */
    @Test
    public void testRepo() {
        coverageRepository.save(april27);
        coverageRepository.save(april28);
        Optional<Coverage> coverageDate = coverageRepository.findFirstByProjectNameAndBranchNameAndBuildNumberAndDateOrderByIdDesc("project", "branch", "build", LocalDate.of(2017, 4, 27));
        Optional<Coverage> coverageNoDate = coverageRepository.findFirstByProjectNameAndBranchNameAndBuildNumberOrderByIdDesc("project", "branch", "build");
        Optional<Coverage> coverageBadDate = coverageRepository.findFirstByProjectNameAndBranchNameAndBuildNumberAndDateOrderByIdDesc("project", "branch", "build", LocalDate.of(2017, 4, 29));
        Optional<Coverage> coverageBadProjectNoDate = coverageRepository.findFirstByProjectNameAndBranchNameAndBuildNumberOrderByIdDesc("projectBad", "branch", "build");
        //take he number, id will be different probably
        assertEquals(7, coverageDate.get().getCoverage());
        //take he number, id will be different probably
        assertEquals(8, coverageNoDate.get().getCoverage());
        //does not exist for this date
        assertFalse(coverageBadDate.isPresent());
        //does not exist for this projectName
        assertFalse(coverageBadProjectNoDate.isPresent());

    }

}