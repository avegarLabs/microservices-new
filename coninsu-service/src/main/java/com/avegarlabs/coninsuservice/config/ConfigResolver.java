package com.avegarlabs.coninsuservice.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigResolver {

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver(){
        return new KeycloakSpringBootConfigResolver();
    }
}

