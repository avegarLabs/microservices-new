package com.avegarlabs.productionservice.services;

import com.avegarlabs.productionservice.dto.ActivityListItem;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
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
public class HiringUtilsService {

    private final WebClient.Builder webClientBuilder;

    @Cacheable(value = "activities", unless = "#result == null")
    public List<ActivityListItem> getActivityList() {
        List<ActivityListItem> list = new ArrayList<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof KeycloakAuthenticationToken) {
            KeycloakAuthenticationToken keycloakAuthentication = (KeycloakAuthenticationToken) authentication;

            if (keycloakAuthentication.getPrincipal() instanceof KeycloakPrincipal) {
                KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) keycloakAuthentication.getPrincipal();
                KeycloakSecurityContext keycloakSecurityContext = keycloakPrincipal.getKeycloakSecurityContext();
                String token = keycloakSecurityContext.getTokenString();

                ActivityListItem[] activityListItems = webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8082/api/hiring/activity")
                        .header("Authorization", "Bearer " + token)
                        .retrieve()
                        .bodyToMono(ActivityListItem[].class)
                        .block();

                if (activityListItems != null) {
                    list = Arrays.asList(activityListItems);
                }
            }
        }

        return list;
    }
}
