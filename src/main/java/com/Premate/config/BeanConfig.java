package com.Premate.config;


import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Configuration
public class BeanConfig {
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
	@Bean
    Date localDate() {
    	return new Date();
    }
	
}
