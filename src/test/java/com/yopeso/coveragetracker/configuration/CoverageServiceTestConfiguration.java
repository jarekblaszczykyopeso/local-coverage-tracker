package com.yopeso.coveragetracker.configuration;

import com.yopeso.coveragetracker.domain.CoverageRequest;
import com.yopeso.coveragetracker.service.CoverageService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

import static org.mockito.Mockito.when;

@Profile("test")
@Configuration
public class CoverageServiceTestConfiguration {
    @Bean
    @Primary
    public CoverageService coverageService() {
        final CoverageService coverageService = Mockito.mock(CoverageService.class);
        when(coverageService.getCoverage(Mockito.eq(new CoverageRequest("project", "branch", "build")))).thenReturn(Optional.of(7));
        when(coverageService.getCoverage(Mockito.eq(new CoverageRequest("projectBad", "branch", "build")))).thenReturn(Optional.empty());
        return coverageService;
    }
}