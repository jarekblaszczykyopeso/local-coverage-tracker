package com.yopeso.coveragetracker.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Testing entity.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Coverage.class)
public class CoverageTest {
    Coverage coverageAllArgsConstructor = new Coverage(null, "project", "branch", "build", LocalDate.of(2017, 4, 27), 7);

    /**
     * Simple test with 1 constructor.
     */
    @Test
    public void testModel() {
        Coverage coverageAllExpected = new Coverage(null, "project", "branch", "build", LocalDate.of(2017, 4, 27), 7);
        assertEquals(coverageAllExpected, coverageAllArgsConstructor);
    }
}