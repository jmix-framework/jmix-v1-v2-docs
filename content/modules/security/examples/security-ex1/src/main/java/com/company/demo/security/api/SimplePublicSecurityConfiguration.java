package com.company.demo.security.api;

import io.jmix.core.JmixSecurityFilterChainOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Profile("endpoints-security")
//tag::whole-class[]
@Configuration
public class SimplePublicSecurityConfiguration {

    //tag::public-filter-chain[]
    @Bean
    @Order(JmixSecurityFilterChainOrder.CUSTOM)
    SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/public-api/**")
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().permitAll()
                );
        return http.build();
    }
    //end::public-filter-chain[]
}
//end::whole-class[]