package com.yopeso.coveragetracker.service;

import com.yopeso.coveragetracker.domain.CoveragePK;
import com.yopeso.coveragetracker.domain.CoverageRepository;
import com.yopeso.coveragetracker.domain.Measurement;
import com.yopeso.coveragetracker.domain.requests.Branch;
import com.yopeso.coveragetracker.domain.requests.Build;
import com.yopeso.coveragetracker.domain.responses.CoverageResponse;
import org.junit.After;
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

    private final String project = "project";
    private final String branch = "branch";

    @Before
    public void setUp() throws Exception {
        repository = mock(CoverageRepository.class);
        service = new CoverageServiceImpl(repository);
    }

    @Test
    public void testSaveCoverage() throws Exception {
        final Measurement measurement = mock(Measurement.class);
        service.saveCoverage(measurement);
        verify(repository, Mockito.times(1)).save(measurement);
    }


    @Test
    public void testGetCoverage() throws Exception {
        final int build = 1;

        final Measurement measurement = mock(Measurement.class);
        when(measurement.getCoverage()).thenReturn(99);
        final Optional<Measurement> expectedMock = Optional.of(measurement);
        when(repository.findByCoveragePK_ProjectNameAndCoveragePK_BranchNameAndCoveragePK_BuildNumber(project, branch, build)).thenReturn(expectedMock);

        final Optional<Integer> actual = service.getCoverage(new Build(project, branch, build));
        assertTrue(actual.isPresent());
        assertEquals(Integer.valueOf(99), actual.orElseThrow(RuntimeException::new));
    }

    @Test
    public void testGetLastCoverage() throws Exception {

        final Measurement measurement = mock(Measurement.class);
        when(measurement.getCoverage()).thenReturn(99);
        final Optional<Measurement> expectedMock = Optional.of(measurement);
        when(repository.findFirstByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberDesc(project, branch)).thenReturn(expectedMock);

        final Optional<Integer> actual = service.getLastCoverage(new Branch(project, branch));
        assertTrue(actual.isPresent());
        assertEquals(Integer.valueOf(99), actual.orElseThrow(RuntimeException::new));
    }

    @Test
    public void testGetBranchCoverage() throws Exception {

        final Measurement measurement1 = mock(Measurement.class);
        when(measurement1.getCoverage()).thenReturn(1);
        when(measurement1.getCoveragePK()).thenReturn(new CoveragePK(project, branch, 1));

        final Measurement measurement2 = mock(Measurement.class);
        when(measurement2.getCoverage()).thenReturn(2);
        when(measurement2.getCoveragePK()).thenReturn(new CoveragePK(project, branch, 2));

        final Optional<List<Measurement>> expectedMock = Optional.of(Arrays.asList(measurement1, measurement2));
        when(repository.findByCoveragePK_ProjectNameAndCoveragePK_BranchNameOrderByCoveragePK_BuildNumberAsc(project, branch)).thenReturn(expectedMock);

        final Optional<List<CoverageResponse>> actual = service.getBranchCoverage(new Branch(project, branch));
        assertTrue(actual.isPresent());
        assertEquals(1, actual.get().get(0).getCoverage());
    }

    @Test
    public void testGetProjectCoverage() throws Exception {

        final Measurement measurement1 = mock(Measurement.class);
        when(measurement1.getCoverage()).thenReturn(11);
        final String branch1 = "branch1";
        when(measurement1.getCoveragePK()).thenReturn(new CoveragePK(project, branch1, 1));

        final Measurement measurement2 = mock(Measurement.class);
        when(measurement2.getCoverage()).thenReturn(22);
        when(measurement2.getCoveragePK()).thenReturn(new CoveragePK(project, branch1, 2));

        final Measurement measurement3 = mock(Measurement.class);
        when(measurement3.getCoverage()).thenReturn(77);
        final String branch2 = "branch2";
        when(measurement3.getCoveragePK()).thenReturn(new CoveragePK(project, branch2, 2));

        final Optional<List<Measurement>> expectedMock = Optional.of(Arrays.asList(measurement1, measurement2, measurement3));
        when(repository.findByCoveragePK_ProjectNameOrderByCoveragePK_BranchNameAscCoveragePK_BuildNumberAsc(project)).thenReturn(expectedMock);

        final Optional<List<CoverageResponse>> actual = service.getProjectCoverage(project);

        assertTrue(actual.isPresent());
        assertEquals(77, actual.get().get(2).getCoverage());
    }

    @After
    public void clearData() {
        repository.deleteAll();
    }
}