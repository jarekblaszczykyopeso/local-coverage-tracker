package com.yopeso.coveragetracker.web;

import com.yopeso.coveragetracker.domain.Coverage;
import com.yopeso.coveragetracker.domain.CoverageRequest;
import com.yopeso.coveragetracker.exception.ResourceNotFoundException;
import com.yopeso.coveragetracker.service.CoverageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CoverageController {
    private final CoverageService coverageService;

    @RequestMapping(method = RequestMethod.GET, value = "/{project}/{branch}/{build}")
    public int getCoverage(@PathVariable String project, @PathVariable String branch, @PathVariable int build) {

        final CoverageRequest coverageRequest = new CoverageRequest(project, branch, build);
        final Optional<Integer> coverageOptional = coverageService.getCoverage(coverageRequest);
        return coverageOptional.orElseThrow(ResourceNotFoundException::new);

    }


    @RequestMapping(method = RequestMethod.PUT, value = "/{project}/{branch}/{build}")
    public void putCoverage(@PathVariable String project, @PathVariable String branch, @PathVariable int build, @RequestBody int coverage) {

        coverageService.saveCoverage(new Coverage(project, branch, build, coverage));
    }
}
