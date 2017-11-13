package com.yopeso.coveragetracker.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Rest controller for the coverage.
 * There are 2 gets web services, one with data and the other one without date.
 * Both of them are using the same method.
 */
@RestController
@RequiredArgsConstructor
public class CoverageController {
//    @Autowired
//    CoverageService coverageService;


    /**
     * Returns coverage for the given project, branch, build.
     *
     * @param projectName project name
     * @param branchName  branch name
     * @param buildNumber build number
     * @return int the last coverage for given parameters (without date) found in db or -1 if is not found
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{projectName}/{branchName}/{buildNumber}")
    public int getCoverage(@PathVariable String projectName, @PathVariable String branchName,
                           @PathVariable String buildNumber) {
        return getCoverage(projectName, branchName, buildNumber, null);
    }

    /**
     * Returns coverage for the given project, branch, build, date.
     *
     * @param projectName project name
     * @param branchName  branch name
     * @param buildNumber build number
     * @param date        date in the format yyyy-MM-dd
     * @return int the last coverage for given parameters (with date) found in db or -1 if is not found
     */

    @RequestMapping(method = RequestMethod.GET, value = "/{projectName}/{branchName}/{buildNumber}/{date}")
    public int getCoverageWithDate(@PathVariable String projectName, @PathVariable String branchName,
                                   @PathVariable String buildNumber, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return getCoverage(projectName, branchName, buildNumber, date);
    }


    /**
     * Create the coverage record in db.
     * When the record is created return 201 status and in header url for get request for the coverage; 204 status otherwise.
     *
     * @param projectName         project name
     * @param branchName          branch name
     * @param buildNumber         build number
     * @param coverageMeasurement json containing coverage put into the body of request
     * @return ResponseEntity;
     */
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

    //TODO change it and use the service with null also

    /**
     * Auxiliary method used in both get web services. Here is the logic.
     *
     * @param projectName project name
     * @param branchName  branch name
     * @param buildNumber build number
     * @param date        date in the format yyyy-MM-dd
     * @return int the last coverage for given parameters (with or without date) found in db or -1 if is not found
     */
    private int getCoverage(String projectName, String branchName, String buildNumber, LocalDate date) {
        if (date != null) {
            return 77;
        } else {
            return -9;
        }
    }

}

/**
 * POJO class for put the coverage in the request body.
 */
@Data
@AllArgsConstructor
class CoverageMeasurement {
    int coverage;
}

/**
 * Class with the security configuration.
 */
//@Configuration
//@EnableWebSecurity
//class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//    @Bean
//    UserDetailsService users() {
//        return new InMemoryUserDetailsManager(Collections.singleton(User.withUsername("user").roles("ADMIN").password("pwd").build()));
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        http.csrf().disable();
//    }
//}
