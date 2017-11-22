package com.yopeso.coveragetracker.service;

import com.yopeso.coveragetracker.domain.Coverage;
import com.yopeso.coveragetracker.domain.CoveragePK;
import com.yopeso.coveragetracker.domain.CoverageRepository;
import com.yopeso.coveragetracker.domain.requests.BranchRequest;
import com.yopeso.coveragetracker.domain.requests.BuildRequest;
import com.yopeso.coveragetracker.domain.responses.CoverageResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CoverageServiceImplTest {

    private CoverageRepository repository;
    private CoverageService service;

    final String project = "project";
    final String branch = "branch";

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
        final int build = 1;

        final Coverage coverage = mock(Coverage.class);
        when(coverage.getCoverage()).thenReturn(99);
        final Optional<Coverage> expectedMock = Optional.of(coverage);
        when(repository.findByCoveragePK_ProjectNameAndCoveragePK_BranchNameAndCoveragePK_BuildNumber(project, branch, build)).thenReturn(expectedMock);

        final Optional<Integer> actual = service.getCoverage(new BuildRequest(project, branch, build));
        assertTrue(actual.isPresent());
        assertEquals(Integer.valueOf(99), actual.orElseThrow(RuntimeException::new));
    }

    @Test
    public void testGetLastCoverage() throws Exception {

        final Coverage coverage = mock(Coverage.class);
        when(coverage.getCoverage()).thenReturn(99);
        final Optional<Coverage> expectedMock = Optional.of(coverage);
        when(repository.findFirstByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberDesc(project, branch)).thenReturn(expectedMock);

        final Optional<Integer> actual = service.getLastCoverage(new BranchRequest(project, branch));
        assertTrue(actual.isPresent());
        assertEquals(Integer.valueOf(99), actual.orElseThrow(RuntimeException::new));
    }

    @Test
    public void testGetBranchCoverage() throws Exception {

        final Coverage coverage1 = mock(Coverage.class);
        when(coverage1.getCoverage()).thenReturn(1);
        when(coverage1.getCoveragePK()).thenReturn(new CoveragePK(project, branch, 1));

        final Coverage coverage2 = mock(Coverage.class);
        when(coverage2.getCoverage()).thenReturn(2);
        when(coverage2.getCoveragePK()).thenReturn(new CoveragePK(project, branch, 2));

        final Optional<List<Coverage>> expectedMock = Optional.of(Arrays.asList(coverage1, coverage2));
        when(repository.findByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberAsc(project, branch)).thenReturn(expectedMock);

        final Optional<List<CoverageResponse>> actual = service.getBranchCoverage(new BranchRequest(project, branch));
        assertTrue(actual.isPresent());
        assertEquals(1, actual.get().get(0).getCoverage());
    }

    @Test
    public void testGetProjectCoverage() throws Exception {

        final Coverage coverage1 = mock(Coverage.class);
        when(coverage1.getCoverage()).thenReturn(11);
        final String branch1 = "branch1";
        when(coverage1.getCoveragePK()).thenReturn(new CoveragePK(project, branch1, 1));

        final Coverage coverage2 = mock(Coverage.class);
        when(coverage2.getCoverage()).thenReturn(22);
        when(coverage2.getCoveragePK()).thenReturn(new CoveragePK(project, branch1, 2));

        final Coverage coverage3 = mock(Coverage.class);
        when(coverage3.getCoverage()).thenReturn(77);
        final String branch2 = "branch2";
        when(coverage3.getCoveragePK()).thenReturn(new CoveragePK(project, branch2, 2));

        final Optional<List<Coverage>> expectedMock = Optional.of(Arrays.asList(coverage1, coverage2, coverage3));
        when(repository.findByCoveragePK_ProjectNameOrderByCoveragePK_BranchNameAscCoveragePK_BuildNumberAsc(project)).thenReturn(expectedMock);

        final Optional<List<CoverageResponse>> actual = service.getProjectCoverage(project);

        assertTrue(actual.isPresent());
        assertEquals(77, actual.get().get(2).getCoverage());
    }
}