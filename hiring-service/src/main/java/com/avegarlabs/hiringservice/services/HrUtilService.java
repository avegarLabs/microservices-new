package com.avegarlabs.hiringservice.services;

import com.avegarlabs.hiringservice.dto.*;
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
    public PersonalListResponse personalListResponse;

    @Cacheable(value = "departments", unless = "#result == null")
    public DepartamentListResponse getDepartamentList() {
        return webClientBuilder.build()
                .get()
                .uri("http://127.0.0.1:8000/api/departament/")
                .retrieve()
                .bodyToMono(DepartamentListResponse.class)
                .block();


    }

    @Cacheable(value = "types", unless = "#result == null")
    public ActTypeListResponse getTypesList() {
        return webClientBuilder.build()
                .get()
                .uri("http://127.0.0.1:8000/api/types/")
                .retrieve()
                .bodyToMono(ActTypeListResponse.class)
                .block();
    }

    @Cacheable(value = "workers", unless = "#result == null")
    public PersonalListResponse getPersonalList() {
        return webClientBuilder.build()
                .get()
                .uri("http://127.0.0.1:8000/api/personal/")
                .retrieve()
                .bodyToMono(PersonalListResponse.class)
                .block();
    }

    @Cacheable(value = "charges", unless = "#result == null")
    public ChargesListResponse getChargeslList() {
        return webClientBuilder.build()
                .get()
                .uri("http://127.0.0.1:8000/api/cargos/")
                .retrieve()
                .bodyToMono(ChargesListResponse.class)
                //.doOnNext(System.out::println)
                .block();
    }

    public DepartamentListItem getDepartamentById(int id) {
        return getDepartamentList().getDepartamentos().parallelStream().filter(item -> item.getId() == id).findFirst().get();
    }


    public WorkerListItem findWorkerById(int id) {
        if (personalListResponse == null) {
            personalListResponse = getPersonalList();
        }
        return personalListResponse.getPersonal().parallelStream().filter(w -> w.getId() == id).findFirst().get();
    }
}
