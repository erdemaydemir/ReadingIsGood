package com.erdem.readingisgood.config;

import com.erdem.readingisgood.rest.controller.error.CustomHandlerExceptionResolver;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public DozerBeanMapper dozerBeanMapper() {
        return new DozerBeanMapper();
    }

    @Bean
    public CustomHandlerExceptionResolver customHandlerExceptionResolver() {
        return new CustomHandlerExceptionResolver();
    }
}
