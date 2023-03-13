/*
 * Author : Naveen Kumar
 * Date : 09-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/v1/borrower/**")
                        .uri("lb://borrower-service"))
                .route(p -> p
                        .path("/api/v1/authentication/**")
                        .uri("lb://authentication"))
                .route(p -> p
                        .path("/api/v1/lender/**")
                        .uri("lb://lender-service"))

                .build();
    }


}
