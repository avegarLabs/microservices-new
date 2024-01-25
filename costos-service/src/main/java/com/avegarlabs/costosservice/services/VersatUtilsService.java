package com.avegarlabs.costosservice.services;

import com.avegarlabs.costosservice.dto.DatNomListItem;
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
public class VersatUtilsService {

    private final WebClient.Builder webClientBuilder;

    @Cacheable(value = "datNom", unless = "#result == null")
    public List<DatNomListItem> getDatNomList(){

        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) authentication.getPrincipal();
        KeycloakSecurityContext keycloakSecurityContext = keycloakPrincipal.getKeycloakSecurityContext();
        String token = keycloakSecurityContext.getTokenString();

        List<DatNomListItem> list = new ArrayList<>();

        DatNomListItem[] datNomListItems = webClientBuilder.build()
                .get()
                .uri("http://localhost:8087/api/versat/datNom")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(DatNomListItem[].class)
                .block();

        if(datNomListItems != null) {
            list = Arrays.stream(datNomListItems).toList();
        }
        return list;
    }
}

