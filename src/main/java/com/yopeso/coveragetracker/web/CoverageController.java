package com.yopeso.coveragetracker.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for the coverage.
 */
@RestController
public class CoverageController {
    /**
     * Defines the request hello. To be deleted.
     *
     * @return
     */
    @RequestMapping("/hello")
    public String hello() {
        return "Hi from Spring!";
    }
}