package com.yopeso.coveragetracker.web;

import com.yopeso.coveragetracker.domain.CoverageRequest;
import com.yopeso.coveragetracker.exception.ResourceNotFoundException;
import com.yopeso.coveragetracker.service.CoverageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CoverageController {
    private final CoverageService coverageService;

    @RequestMapping(method = RequestMethod.GET, value = "/{projectName}/{branchName}/{buildNumber}")
    public int getCoverage(@PathVariable String projectName, @PathVariable String branchName,
                           @PathVariable String buildNumber) {

        final CoverageRequest coverageRequest = new CoverageRequest(projectName, branchName, buildNumber);
        final Optional<Integer> coverageOptional = coverageService.getCoverage(coverageRequest);
        return coverageOptional.orElseThrow(ResourceNotFoundException::new);

    }


    @RequestMapping(method = RequestMethod.POST, value = "/{projectName}/{branchName}/{buildNumber}")
    public ResponseEntity<?> postCoverage(@PathVariable String projectName, @PathVariable String branchName,
                                          @PathVariable String buildNumber, @RequestBody CoverageMeasurement coverageMeasurement) {
        //not saved record to db
        //TODO change it!!!
        if (coverageMeasurement.coverage < 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + LocalDate.now().toString()).build().toUri()).build();
        }
    }
}

@Data
@AllArgsConstructor
class CoverageMeasurement {
    int coverage;
}
