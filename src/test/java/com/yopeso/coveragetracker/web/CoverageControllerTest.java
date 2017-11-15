package com.yopeso.coveragetracker.web;

import com.yopeso.coveragetracker.domain.CoverageRequest;
import com.yopeso.coveragetracker.exception.BadRequestException;
import com.yopeso.coveragetracker.service.CoverageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
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
    public void testGet() {
        final String getPath = "/project/branch/build";
        final String project = "project";
        final String branch = "branch";
        final String build = "build";
        when(coverageService.getCoverage(eq(new CoverageRequest(project, branch, build)))).thenReturn(Optional.of(7));
        final ResponseEntity<Integer> responseEntity = restTemplate.getForEntity(getPath, Integer.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(7, responseEntity.getBody().intValue());
    }

    @Test
    public void testGetBad() {
        final String getBadPath = "/projectBad/branch/build";
        final String projectBad = "projectBad";
        final String branch = "branch";
        final String build = "build";
        when(coverageService.getCoverage(eq(new CoverageRequest(projectBad, branch, build)))).thenReturn(Optional.empty());
        final ResponseEntity<?> responseEntity = restTemplate.getForEntity(getBadPath, Object.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testPut() {
        final String putPath = "/projectPut/branch/build";
        final ResponseEntity<Object> putResponse = restTemplate.exchange(putPath, HttpMethod.PUT, new HttpEntity<>(7), Object.class);
        verify(coverageService, times(1)).saveCoverage(any());
        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
    }

    @Test
    public void testPutBad() {
        final String putPath = "/projectPutBad/branch/build";
        doThrow(new BadRequestException()).when(coverageService).saveCoverage(any());
        final ResponseEntity<Object> putResponse = restTemplate.exchange(putPath, HttpMethod.PUT, new HttpEntity<>(7), Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, putResponse.getStatusCode());
    }
}

