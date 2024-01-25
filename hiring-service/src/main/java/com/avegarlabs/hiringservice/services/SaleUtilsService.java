package com.avegarlabs.hiringservice.services;


import com.avegarlabs.hiringservice.dto.ResumeSaleList;
import com.avegarlabs.hiringservice.dto.SaleListItem;
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
public class SaleUtilsService {

    private final WebClient.Builder webClientBuilder;

    @Cacheable(value = "sales", unless = "#result == null")
    public List<SaleListItem> getAllSalesList(){

        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) authentication.getPrincipal();
        KeycloakSecurityContext keycloakSecurityContext = keycloakPrincipal.getKeycloakSecurityContext();
        String token = keycloakSecurityContext.getTokenString();

        List<SaleListItem> list = new ArrayList<>();

        SaleListItem[] saleListItems = webClientBuilder.build()
                .get()
                .uri("http://localhost:8083/api/sale/all")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(SaleListItem[].class)
                .block();

        if(saleListItems != null) {
            list = Arrays.stream(saleListItems).toList();
        }
        return list;
    }



}
