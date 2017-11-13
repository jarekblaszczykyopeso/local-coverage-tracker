package com.yopeso.coveragetracker.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;


/**
 * Tests for coverage controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoverageControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int localPort;

    String postBadPath = "/projectName/branchName/buildsPath/somethingMoreBad/oneMoreBad";
    String postGoodPath = "/projectName/branchName/buildNumber";
    String getBadPath = "/projectName/branchName/buildNumber/firstBad/secondBad";
    String getNoDatePath = "/projectName/branchName/buildNumber";
    String getDateExistsPath = "/projectName/branchName/buildNumber/" + LocalDate.now().toString();
    //doesn't exist the coverage from the future
    String getDateNoExistsPath = "/projectName/branchName/buildNumber/" + LocalDate.now().plusDays(3).toString();


    /**
     * Testing the requests according to the scenario: post bad path, post one good, get bad path, get no date, get good date, get bad date.
     * In the response are verified status, location data in header(one case) and integer in body (3 cases)
     */
    //TODO change the test according to scenario: post bad path, post one good, get bad path, get no date, get good date, get bad date
    @Test
    public void testController() {
        //bad path for post - status checked (entity ? / null, because otherwise error)
        ResponseEntity<?> responsePostBad = restTemplate.postForEntity(
                postBadPath, new CoverageMeasurement(7), null);
        assertEquals(404, responsePostBad.getStatusCodeValue());
//
        //good path for post - status and Location in header checked
        ResponseEntity<?> responsePostGood = restTemplate.postForEntity(
                postGoodPath, new CoverageMeasurement(7), null);
        assertEquals(201, responsePostGood.getStatusCodeValue());
        String expectedUri = URI.create("http://localhost:" + localPort + postGoodPath + "/" + LocalDate.now().toString()).toString();
        assertEquals(expectedUri, responsePostGood.getHeaders().get("Location").get(0));

        //bad path for get - status checked (entity ? / null, because otherwise error)
        ResponseEntity<?> responseGetBad = restTemplate.getForEntity(getBadPath, null);
        assertEquals(404, responseGetBad.getStatusCodeValue());

        //good path for get with no date - status and result (last coverage) checked
        ResponseEntity<Integer> responseGetNoDate = restTemplate.getForEntity(getNoDatePath, Integer.class);
        assertEquals(200, responseGetNoDate.getStatusCodeValue());
        assertEquals(-9, responseGetNoDate.getBody().intValue());

        //good path for get with good date - status and result (last coverage) checked
        ResponseEntity<Integer> responseGetDateExists = restTemplate.getForEntity(getDateExistsPath, Integer.class);
        assertEquals(200, responseGetDateExists.getStatusCodeValue());
        assertEquals(77, responseGetDateExists.getBody().intValue());

        //good path for get with not existing date - status and result (last coverage) checked
        ResponseEntity<Integer> responseGetDateNoExists = restTemplate.getForEntity(getDateNoExistsPath, Integer.class);
        assertEquals(200, responseGetDateNoExists.getStatusCodeValue());
        //TODO change it to -1 return
        // assertEquals(77, responseGetDateNoExists.getBody().intValue());
    }
}
