package com.yopeso.coveragetracker.service;

import com.yopeso.coveragetracker.CoverageTrackerApplication;
import com.yopeso.coveragetracker.domain.CoveragePK;
import com.yopeso.coveragetracker.domain.CoverageRepository;
import com.yopeso.coveragetracker.domain.Measurement;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoverageTrackerApplication.class)
public class CoverageServiceImplValidationTest {
    @Autowired
    private CoverageRepository repository;

    @Autowired
    private CoverageService service = new CoverageServiceImpl(repository);


    @Test(expected = org.springframework.transaction.TransactionSystemException.class)
    public void testSaveBad101Coverage() {
        service.saveCoverage(new Measurement(101, new CoveragePK("a", "b", 3)));
    }

    @Test(expected = org.springframework.transaction.TransactionSystemException.class)
    public void testSaveBadMinus1Coverage() {
        service.saveCoverage(new Measurement(-1, new CoveragePK("a", "b", 3)));
    }

    @Test(expected = org.springframework.transaction.TransactionSystemException.class)
    public void testSaveBad0Build() {
        service.saveCoverage(new Measurement(77, new CoveragePK("a", "b", 0)));
    }


    @Test
    public void testSaveOk() {
        service.saveCoverage(new Measurement(100, new CoveragePK("a", "b", 3)));
    }

    @After
    public void clearData() {
        repository.deleteAll();
    }

}