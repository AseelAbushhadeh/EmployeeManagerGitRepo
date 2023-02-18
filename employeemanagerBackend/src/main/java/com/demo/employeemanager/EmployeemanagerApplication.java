package com.demo.employeemanager;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@SpringBootApplication
public class EmployeemanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeemanagerApplication.class, args);
	}
	@Bean
	public org.springframework.web.filter.CorsFilter corsFilter()
	{
		CorsConfiguration corsConfiguration=new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200/"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin","Access-control-Allow-Origin","Content-Type",
				"Accept","Authorization","Origin, Accept","X-Requested-with",
				"Access-Control-Request-Method","Access-Control-Request-Headers" ));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin","Content-Type","Accept","Authorization",
				"Access-control-Allow-Origin", "Access-control-Allow-Origin","Access-control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource=new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
		return new org.springframework.web.filter.CorsFilter(urlBasedCorsConfigurationSource);

	}

}
