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
        final String build = "build";
        final Coverage april27 = new Coverage(null, project, branch, build, LocalDate.of(2017, 4, 27), 7);
        final Coverage april28 = new Coverage(null, project, branch, build, LocalDate.of(2017, 4, 28), 8);
        coverageRepository.save(april27);
        coverageRepository.save(april28);
        Optional<Coverage> optionalCoverage = coverageRepository.findFirstByProjectNameAndBranchNameAndBuildNumberOrderByIdDesc(project, branch, build);
        assertTrue(optionalCoverage.isPresent());
        assertEquals(8, optionalCoverage.orElseThrow(RuntimeException::new).getCoverage());
    }
}