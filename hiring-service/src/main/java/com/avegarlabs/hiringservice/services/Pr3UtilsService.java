package com.avegarlabs.hiringservice.services;


import com.avegarlabs.hiringservice.dto.Pr3ListItem;
import com.avegarlabs.hiringservice.dto.ResumeProdActList;
import com.avegarlabs.hiringservice.dto.ResumeSaleList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class Pr3UtilsService {

    private final WebClient.Builder webClientBuilder;

    @Cacheable(value = "pr3Resume", unless = "#result == null")
    public List<Pr3ListItem> getProductionList(){
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) authentication.getPrincipal();
        KeycloakSecurityContext keycloakSecurityContext = keycloakPrincipal.getKeycloakSecurityContext();
        String token = keycloakSecurityContext.getTokenString();

        List<Pr3ListItem> list = new ArrayList<>();

        Pr3ListItem[] pr3ListItems = webClientBuilder.build()
                .get()
                .uri("http://localhost:8085/api/pr3")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(Pr3ListItem[].class)
                .block();

        if(pr3ListItems != null) {
            list = Arrays.stream(pr3ListItems).toList();
        }
        return list;
    }
}

