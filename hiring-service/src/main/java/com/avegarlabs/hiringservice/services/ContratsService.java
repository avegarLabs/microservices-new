package com.avegarlabs.hiringservice.services;


import com.avegarlabs.hiringservice.dto.*;
import com.avegarlabs.hiringservice.models.Bank;
import com.avegarlabs.hiringservice.models.Contracts;
import com.avegarlabs.hiringservice.models.ServiceRequest;
import com.avegarlabs.hiringservice.repositories.BankRespository;
import com.avegarlabs.hiringservice.repositories.ContractsRespository;
import com.avegarlabs.hiringservice.utils.CreateMoniker;
import com.avegarlabs.hiringservice.utils.DateData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContratsService {

    private final ContractsRespository respository;
    private final ServiceRequestService serviceRequestService;
    private final DateData dateData;
    private final CreateMoniker moniker;

    public ContractsListItem findContractsByNumber(String number){
        Contracts contracts = respository.findByNumber(number);
        return mapContractsListItemToContracts(contracts);
    }

    public ContractsListItem findContractsByMoniker(String moniker){
        Contracts contracts = respository.findByMoniker(moniker);
        return mapContractsListItemToContracts(contracts);
    }



    public List<ContractsListItem> findAll() {
        return respository.findAll().stream().map(this::mapContractsListItemToContracts).collect(Collectors.toList());
    }

    public ContractsListItem addContracts(ContractsModel model) {
        Contracts entity = mapContractsToContractsModel(model);
        respository.save(entity);
        return mapContractsListItemToContracts(entity);
    }

    public ContractsListItem updateContrats(String id, ContractsModel model) {
        Contracts contracts = respository.findById(id).get();
        Contracts contractsUp = updateContractsToContractsModel(contracts, model);
        respository.save(contractsUp);
        return mapContractsListItemToContracts(contractsUp);
    }

    public void deleteContracts(String id) {
        respository.deleteById(id);
    }

    public ContractsListItem mapContractsListItemToContracts(Contracts contracts) {
        ServiceRequest serviceRequest = serviceRequestService.getRequestById(contracts.getServiceRequest().getId());
        ServiceRequestListItem item = serviceRequestService.mapListItemToServiceRequest(serviceRequest);
        return ContractsListItem.builder()
                .id(contracts.getId())
                .name(contracts.getName())
                .about(contracts.getAbout())
                .aproveDate(contracts.getAproveDate())
                .description(contracts.getDescription())
                .serviceRequest(item)
                .endDate(contracts.getEndDate())
                .number(contracts.getNumber())
                .state(contracts.getState())
                .moniker(contracts.getMoniker())
                .build();
    }

    public Contracts mapContractsToContractsModel(ContractsModel model) {
        ServiceRequest serviceRequest = serviceRequestService.getRequestById(model.getServiceRequestId());
        String slug = moniker.createMoniker(model.getName());
        return Contracts.builder()
                .name(model.getName())
                .about(model.getAbout())
                .aproveDate(dateData.checkDate(model.getAproveDate()))
                .description(model.getDescription())
                .serviceRequest(serviceRequest)
                .endDate(dateData.checkDate(model.getEndDate()))
                .number(model.getNumber())
                .state(model.getState())
                .creationDate(dateData.currentMonth())
                .moniker(slug)
                .build();
    }

    public Contracts updateContractsToContractsModel(Contracts entity, ContractsModel model) {
        ServiceRequest serviceRequest = serviceRequestService.getRequestById(model.getServiceRequestId());
        String slug = moniker.createMoniker(model.getName());
        return Contracts.builder()
                .id(entity.getId())
                .name(model.getName())
                .about(model.getAbout())
                .aproveDate(dateData.checkDate(model.getAproveDate()))
                .description(model.getDescription())
                .endDate(dateData.checkDate(model.getEndDate()))
                .number(model.getNumber())
                .state(model.getState())
                .moniker(slug)
                .serviceRequest(serviceRequest)
                .build();
    }
}
