package com.avegarlabs.facturationsalesservice.services;

import com.avegarlabs.facturationsalesservice.dto.ActTypeListResponse;
import com.avegarlabs.facturationsalesservice.dto.DepartamentListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional
public class HrUtilService {

    private final WebClient.Builder webClientBuilder;

    @Cacheable(value = "departments", unless = "#result == null")
    public DepartamentListResponse getDepartamentList(){
        return webClientBuilder.build()
                .get()
                .uri("http://127.0.0.1:8000/api/departament/")
                .retrieve()
                .bodyToMono(DepartamentListResponse.class)
                .block();


    }


    @Cacheable(value = "types", unless = "#result == null")
    public ActTypeListResponse getTypesList(){
        return webClientBuilder.build()
                .get()
                .uri("http://127.0.0.1:8000/api/types/")
                .retrieve()
                .bodyToMono(ActTypeListResponse.class)
                .block();
    }


}
