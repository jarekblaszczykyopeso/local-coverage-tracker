package com.yopeso.coveragetracker.web;

import com.yopeso.coveragetracker.domain.Coverage;
import com.yopeso.coveragetracker.domain.requests.BranchRequest;
import com.yopeso.coveragetracker.domain.requests.BuildRequest;
import com.yopeso.coveragetracker.domain.responses.CoverageResponse;
import com.yopeso.coveragetracker.exception.BadRequestException;
import com.yopeso.coveragetracker.service.CoverageService;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoverageControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CoverageService coverageService;


    @Test
    public void testPut() {
        final String putPath = "/projectPut/branch/1";
        final ResponseEntity<Object> putResponse = restTemplate.exchange(putPath, HttpMethod.PUT, new HttpEntity<>(7), Object.class);
        verify(coverageService, times(1)).saveCoverage(any());
        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
    }

    @Test
    public void testPutBad() {
        final String putPath = "/projectPutBad/branch/1";
        doThrow(new BadRequestException()).when(coverageService).saveCoverage(any());
        final ResponseEntity<Object> putResponse = restTemplate.exchange(putPath, HttpMethod.PUT, new HttpEntity<>(7), Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, putResponse.getStatusCode());
    }

    @Test
    public void testGet() {
        final String getPath = "/project/branch/1";
        final String project = "project";
        final String branch = "branch";
        final int build = 1;
        when(coverageService.getCoverage(eq(new BuildRequest(project, branch, build)))).thenReturn(Optional.of(7));
        final ResponseEntity<Integer> responseEntity = restTemplate.getForEntity(getPath, Integer.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(7, responseEntity.getBody().intValue());
    }

    @Test
    public void testGetBad() {
        final String getBadPath = "/projectBad/branch/1";
        final String projectBad = "projectBad";
        final String branch = "branch";
        final int build = 1;
        when(coverageService.getCoverage(eq(new BuildRequest(projectBad, branch, build)))).thenReturn(Optional.empty());
        final ResponseEntity<?> responseEntity = restTemplate.getForEntity(getBadPath, Object.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetLast() {
        final String getLastPath = "/project/branch/latest";
        final String project = "project";
        final String branch = "branch";
        when(coverageService.getLastCoverage(eq(new BranchRequest(project, branch)))).thenReturn(Optional.of(7));
        final ResponseEntity<Integer> responseEntity = restTemplate.getForEntity(getLastPath, Integer.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(7, responseEntity.getBody().intValue());
    }

    @Test
    public void testGetBranch() throws JSONException {
        final String getBranchPath = "/project/branch";
        final String project = "project";
        final String branch = "branch";
        final Optional<List<CoverageResponse>> expectedMock = Optional.of(Arrays.asList(new CoverageResponse(new Coverage(project, branch, 1, 11)), new CoverageResponse(new Coverage(project, branch, 2, 22))));
        when(coverageService.getBranchCoverage(eq(new BranchRequest(project, branch)))).thenReturn(expectedMock);
        final ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(getBranchPath, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isEmpty());
        final String expectedJSON = "[{project:project,branch:branch,build:1,coverage:11},{project:project,branch:branch,build:2,coverage:22}]";
        JSONAssert.assertEquals(expectedJSON, responseEntity.getBody(), true);
    }

    @Test
    public void testGetProject() throws JSONException {
        final String getProjectPath = "/project";
        final String project = "project";
        final String branch1 = "branch1";
        final String branch2 = "branch2";
        final Optional<List<CoverageResponse>> expectedMock = Optional.of(Arrays.asList(new CoverageResponse(new Coverage(project, branch1, 1, 11)), new CoverageResponse(new Coverage(project, branch1, 2, 22)), new CoverageResponse(new Coverage(project, branch2, 1, 77))));
        when(coverageService.getProjectCoverage(eq(project))).thenReturn(expectedMock);
        final ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(getProjectPath, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isEmpty());
        final String expectedJSON = "[{project:project,branch:branch1,build:1,coverage:11},{project:project,branch:branch1,build:2,coverage:22},{project:project,branch:branch2,build:1,coverage:77}]";
        JSONAssert.assertEquals(expectedJSON, responseEntity.getBody(), true);
    }
}

