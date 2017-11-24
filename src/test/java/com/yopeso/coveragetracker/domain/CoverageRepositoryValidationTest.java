package com.yopeso.coveragetracker.domain;

import com.yopeso.coveragetracker.CoverageTrackerApplication;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoverageTrackerApplication.class)
public class CoverageRepositoryValidationTest {
    @Autowired
    private CoverageRepository repo;

    @Test(expected = org.springframework.transaction.TransactionSystemException.class)
    public void testSaveBad101Coverage() {
        repo.save(new Measurement(101, new CoveragePK("a", "b", 3)));
    }

    @Test(expected = org.springframework.transaction.TransactionSystemException.class)
    public void testSaveBadMinus1Coverage() {
        repo.save(new Measurement(-1, new CoveragePK("a", "b", 3)));
    }

    @Test(expected = org.springframework.transaction.TransactionSystemException.class)
    public void testSaveBad0Build() {
        repo.save(new Measurement(77, new CoveragePK("a", "b", 0)));
    }


    @Test
    public void testSaveOk() {
        repo.save(new Measurement(100, new CoveragePK("a", "b", 1)));
    }

    @After
    public void clearData() {
        repo.deleteAll();
    }

}
