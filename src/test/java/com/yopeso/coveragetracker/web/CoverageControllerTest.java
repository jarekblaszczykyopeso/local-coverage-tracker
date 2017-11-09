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

    String postBadPath = "/projects/projectName/branches/branchName/buildsBadPath/buildNumber";
    String postGoodPath = "/projects/projectName/branches/branchName/builds/buildNumber";
    String getBadPath = "/projects/projectName/branches/branchName/buildsBadPath/buildNumber";
    String getNoDatePath = "/projects/projectName/branches/branchName/builds/buildNumber";
    String getDateExistsPath = "/projects/projectName/branches/branchName/builds/buildNumber/dates/" + LocalDate.now().toString();
    //doesn't exist the coverage from the future
    String getDateNoExistsPath = "/projects/projectName/branches/branchName/builds/buildNumber/dates/" + LocalDate.now().plusDays(3).toString();


    /**
     * Testing the requests according to the scenario: post bad path, post one good, get bad path, get no date, get good date, get bad date.
     * In the response are verified status, location data in header(one case) and integer in body (3 cases)
     */
    //TODO change the test according to scenario: post bad path, post one good, get bad path, get no date, get good date, get bad date
    @Test
    public void testController() {
        //add authorisation
        TestRestTemplate restTemplateWithAuth = this.restTemplate.withBasicAuth("user", "pwd");

        //bad path for post - status checked (entity ? / null, because otherwise error)
        ResponseEntity<?> responsePostBad = restTemplateWithAuth.postForEntity(
                postBadPath, new CoverageJson(7), null);
        assertEquals(404, responsePostBad.getStatusCodeValue());

        //good path for post - status and Location in header checked
        ResponseEntity<?> responsePostGood = restTemplateWithAuth.postForEntity(
                postGoodPath, new CoverageJson(7), null);
        assertEquals(201, responsePostGood.getStatusCodeValue());
        String expectedUri = URI.create("http://localhost:" + localPort + postGoodPath + "/dates/" + LocalDate.now().toString()).toString();
        assertEquals(expectedUri, responsePostGood.getHeaders().get("Location").get(0));

        //bad path for get - status checked (entity ? / null, because otherwise error)
        ResponseEntity<?> responseGetBad = restTemplateWithAuth.getForEntity(getBadPath, null);
        assertEquals(404, responseGetBad.getStatusCodeValue());

        //good path for get with no date - status and result (last coverage) checked
        ResponseEntity<Integer> responseGetNoDate = restTemplateWithAuth.getForEntity(getNoDatePath, Integer.class);
        assertEquals(200, responseGetNoDate.getStatusCodeValue());
        assertEquals(-9, responseGetNoDate.getBody().intValue());

        //good path for get with good date - status and result (last coverage) checked
        ResponseEntity<Integer> responseGetDateExists = restTemplateWithAuth.getForEntity(getDateExistsPath, Integer.class);
        assertEquals(200, responseGetDateExists.getStatusCodeValue());
        assertEquals(77, responseGetDateExists.getBody().intValue());

        //good path for get with not existing date - status and result (last coverage) checked
        ResponseEntity<Integer> responseGetDateNoExists = restTemplateWithAuth.getForEntity(getDateNoExistsPath, Integer.class);
        assertEquals(200, responseGetDateNoExists.getStatusCodeValue());
        //TODO change it to -1 return
        // assertEquals(77, responseGetDateNoExists.getBody().intValue());
    }
}
