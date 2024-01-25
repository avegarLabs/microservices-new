package com.avegarlabs.costosservice.services;

import com.avegarlabs.costosservice.dto.ConinsuListItem;
import com.avegarlabs.costosservice.dto.Pr3ListItem;
import lombok.RequiredArgsConstructor;
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
public class ConinsuUtilsService {

    private final WebClient.Builder webClientBuilder;

    @Cacheable(value = "coninsu", unless = "#result == null")
    public List<ConinsuListItem> getConinsuList(){

        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) authentication.getPrincipal();
        KeycloakSecurityContext keycloakSecurityContext = keycloakPrincipal.getKeycloakSecurityContext();
        String token = keycloakSecurityContext.getTokenString();

        List<ConinsuListItem> list = new ArrayList<>();

        ConinsuListItem[] coninsuListItems = webClientBuilder.build()
                .get()
                .uri("http://localhost:8084/api/coninsu/month")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(ConinsuListItem[].class)
                .block();

        if(coninsuListItems != null) {
            list = Arrays.stream(coninsuListItems).toList();
        }
        return list;
    }
}

