package com.notesbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class NotesBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesBackendApplication.class, args);
	}

	@Configuration
	public class WebConfig {
	    @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**") // allow all endpoints
	                        .allowedOrigins("http://localhost:3000") // frontend URL
	                        .allowedMethods("*")
	                        .allowedHeaders("*");
	            }
	        };
	    }
	}


}
