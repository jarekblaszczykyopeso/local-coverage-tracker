package com.yopeso.coveragetracker.web;

import com.yopeso.coveragetracker.domain.CoveragePK;
import com.yopeso.coveragetracker.domain.Measurement;
import com.yopeso.coveragetracker.domain.requests.Branch;
import com.yopeso.coveragetracker.domain.requests.Build;
import com.yopeso.coveragetracker.domain.responses.CoverageResponse;
import com.yopeso.coveragetracker.exception.ResourceNotFoundException;
import com.yopeso.coveragetracker.service.CoverageService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coverage")
@RequiredArgsConstructor
public class CoverageController {

    private final CoverageService coverageService;

    @Validated
    @RequestMapping(method = RequestMethod.PUT, value = "/{project}/{branch}/{build}")
    public void putCoverage(@PathVariable String project, @PathVariable String branch, @PathVariable int build, @RequestBody int coverage) {
        coverageService.saveCoverage(new Measurement(coverage, new CoveragePK(project, branch, build)));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{project}/{branch}/{build}")
    public int getCoverage(@PathVariable String project, @PathVariable String branch, @PathVariable int build) {

        final Build buildRequest = new Build(project, branch, build);
        final Optional<Integer> coverageOptional = coverageService.getCoverage(buildRequest);
        return coverageOptional.orElseThrow(ResourceNotFoundException::new);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{project}/{branch}/latest")
    public int getLastCoverage(@PathVariable String project, @PathVariable String branch) {

        final Branch coverageRequest = new Branch(project, branch);
        final Optional<Integer> coverageOptional = coverageService.getLastCoverage(coverageRequest);
        return coverageOptional.orElseThrow(ResourceNotFoundException::new);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{project}/{branch}")
    public List<CoverageResponse> getBranchCoverage(@PathVariable String project, @PathVariable String branch) {
        final Branch coverageRequest = new Branch(project, branch);
        return coverageService.getBranchCoverage(coverageRequest).orElseThrow(ResourceNotFoundException::new);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{project}")
    public List<CoverageResponse> getProjectCoverage(@PathVariable String project) {
        return coverageService.getProjectCoverage(project).orElseThrow(ResourceNotFoundException::new);
    }

}
