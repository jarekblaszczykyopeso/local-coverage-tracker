package com.yopeso.coveragetracker.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoverageRepository.class)
public class CoverageRepositoryTest {
    @Test
    public void testRepo() {
    }
}