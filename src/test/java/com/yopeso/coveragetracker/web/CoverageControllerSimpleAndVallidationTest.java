package com.yopeso.coveragetracker.web;

import com.yopeso.coveragetracker.domain.CoverageRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoverageControllerSimpleAndVallidationTest {
    private MockMvc mock;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mock = MockMvcBuilders.webAppContextSetup(wac).build();
        mock.perform(put("/project/branch/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("1"));
        mock.perform(put("/project/branch/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("2"));
        mock.perform(put("/project/branch23/23")
                .contentType(MediaType.APPLICATION_JSON)
                .content("23"));
    }

    //SIMPLE TESTS
    @Test
    public void testPut() throws Exception {
        mock.perform(put("/projectOther/branch/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGet() throws Exception {
        mock.perform(get("/project/branch/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(equalTo("1")));
    }

    @Test
    public void testGetBad() throws Exception {
        mock.perform(get("/project/branch/77"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(isEmptyString()));

    }

    @Test
    public void testGetLast() throws Exception {
        mock.perform(get("/project/branch/latest"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(equalTo("2")));
    }

    @Test
    public void testGetBranch() throws Exception {
        mock.perform(get("/project/branch"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].project").exists())
                .andExpect(jsonPath("$[0].project", equalTo("project")))
                .andExpect(jsonPath("$[0].coverage").exists())
                .andExpect(jsonPath("$[0].coverage").isNumber())
                .andExpect(jsonPath("$[0].coverage", equalTo(1)));


    }

    @Test
    public void testGetProject() throws Exception {
        mock.perform(get("/project"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].branch").exists())
                .andExpect(jsonPath("$[2].branch", equalTo("branch23")))
                .andExpect(jsonPath("$[2].coverage").exists())
                .andExpect(jsonPath("$[2].coverage").isNumber())
                .andExpect(jsonPath("$[2].coverage", equalTo(23)));

    }


    //VALIDATON
    @Test(expected = org.springframework.web.util.NestedServletException.class)
    public void testPutValidationBadBuild() throws Exception {
        mock.perform(put("/project/branch/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content("77"));
    }

    @Test(expected = org.springframework.web.util.NestedServletException.class)
    public void testPutValidationBadCoverageMinus1() throws Exception {
        mock.perform(put("/project/branch/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("-1"));
    }

    @Test(expected = org.springframework.web.util.NestedServletException.class)
    public void testPutValidationBadCoverage101() throws Exception {
        mock.perform(put("/project/branch/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("101"));
    }

    @After
    public void clearData() {
        wac.getBean(CoverageRepository.class).deleteAll();
    }


}
