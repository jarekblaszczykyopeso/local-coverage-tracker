package com.yopeso.coveragetracker.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoverageController {
    @RequestMapping("/hello")
    public String hello(){
        return "Hi from Spring!";
    }
}
