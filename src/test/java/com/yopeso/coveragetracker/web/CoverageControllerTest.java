package com.yopeso.coveragetracker.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoverageControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testController() {
        ResponseEntity<String> response = this.restTemplate.getForEntity("/hello", String.class);
        assertEquals("Hi from Spring!", response.getBody());

    }
}