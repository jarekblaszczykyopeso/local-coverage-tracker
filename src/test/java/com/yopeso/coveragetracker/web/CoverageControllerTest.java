package com.yopeso.coveragetracker.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoverageControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGet() {
        final String getPath = "/project/branch/build";
        final ResponseEntity<Integer> responseEntity = restTemplate.getForEntity(getPath, Integer.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(7, responseEntity.getBody().intValue());
    }

    @Test
    public void testGetBad() {
        final String getBadPath = "/projectBad/branch/build";
        final ResponseEntity<?> responseEntity = restTemplate.getForEntity(getBadPath, Object.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
