package com.yopeso.coveragetracker.configuration;

import com.yopeso.coveragetracker.service.CoverageService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class CoverageServiceTestConfiguration {
    @Bean
    @Primary
    public CoverageService coverageService() {
        return Mockito.mock(CoverageService.class);
    }
}