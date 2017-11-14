package com.yopeso.coveragetracker.service;

import com.yopeso.coveragetracker.domain.Coverage;
import com.yopeso.coveragetracker.domain.CoverageRepository;
import com.yopeso.coveragetracker.domain.CoverageRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CoverageServiceImplTest {

    private CoverageRepository repository;
    private CoverageService service;

    @Before
    public void setUp() throws Exception {
        repository = mock(CoverageRepository.class);
        service = new CoverageServiceImpl(repository);
    }

    @Test
    public void testSaveCoverage() throws Exception {
        final Coverage coverage = mock(Coverage.class);
        service.saveCoverage(coverage);
        verify(repository, Mockito.times(1)).save(coverage);
    }


    @Test
    public void testGetCoverage() throws Exception {
        final String project = "project";
        final String branch = "branch";
        final String build = "build";

        final Coverage coverage = mock(Coverage.class);
        when(coverage.getCoverage()).thenReturn(99);
        final Optional<Coverage> expected = Optional.of(coverage);
        when(repository.findFirstByProjectNameAndBranchNameAndBuildNumberOrderByIdDesc(project, branch, build)).thenReturn(expected);

        final Optional<Integer> actual = service.getCoverage(new CoverageRequest(project, branch, build));
        assertTrue(actual.isPresent());
        assertEquals(Integer.valueOf(99), actual.orElseThrow(RuntimeException::new));
    }
}