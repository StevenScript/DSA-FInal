package com.example.DSA_Final.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures global CORS settings for the REST API.
 * Allows cross-origin requests from the React frontend running at http://localhost:3000.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Registers CORS mappings to allow cross-origin requests.
     * This setup is required to permit the frontend to interact with the API.
     *
     * @param registry the CORS registry to which mappings are added
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
