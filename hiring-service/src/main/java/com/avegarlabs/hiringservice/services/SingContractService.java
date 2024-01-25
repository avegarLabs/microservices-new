package com.avegarlabs.hiringservice.services;


import com.avegarlabs.hiringservice.dto.ClientListItem;
import com.avegarlabs.hiringservice.dto.SingContractListItem;
import com.avegarlabs.hiringservice.dto.SingContractModel;
import com.avegarlabs.hiringservice.models.Clients;
import com.avegarlabs.hiringservice.models.SingContract;
import com.avegarlabs.hiringservice.repositories.SingContractRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SingContractService {

    private final ClientsService clientsService;
    private final SingContractRespository singContractRespository;

    public List<SingContractListItem> getByClientId(String id) {
        List<SingContract> list = singContractRespository.findByClientId(id);
        return list.stream().map(this::mapSingContractListItemToSingContract).toList();
    }


    public List<SingContractListItem> findAll() {
        return singContractRespository.findAll().stream().map(this::mapSingContractListItemToSingContract).collect(Collectors.toList());
    }

    public SingContractListItem addSingContractk(SingContractModel model) {
        SingContract entity = mapSingContractModel(model);
        singContractRespository.save(entity);
        return mapSingContractListItemToSingContract(entity);
    }

    public SingContractListItem updateSingContract(String id, SingContractModel model) {
        SingContract entity = singContractRespository.findById(id).get();
        SingContract updateEntity = updateSingContractToModel(entity, model);
        singContractRespository.save(updateEntity);
        return mapSingContractListItemToSingContract(updateEntity);
    }

    public void deleteSingContract(String id) {
        singContractRespository.deleteById(id);
    }

    public SingContractListItem mapSingContractListItemToSingContract(SingContract singContract) {
        ClientListItem clientListItem = clientsService.getClientListItemById(singContract.getClient().getId());
        return SingContractListItem.builder()
                .id(singContract.getId())
                .name(singContract.getName())
                .lastName(singContract.getLastName())
                .charge(singContract.getCharge())
                .clientListItem(clientListItem)
                .build();
    }

    public SingContract mapSingContractModel(SingContractModel model) {
        Clients clients = clientsService.getClientById(model.getClientId());
        return SingContract.builder()
                .name(model.getName())
                .lastName(model.getLastName())
                .charge(model.getCharge())
                .client(clients)
                .build();
    }

    public SingContract updateSingContractToModel(SingContract entity, SingContractModel model) {
        Clients clients = clientsService.getClientById(model.getClientId());
        return SingContract.builder()
                .id(entity.getId())
                .name(model.getName())
                .lastName(model.getLastName())
                .charge(model.getCharge())
                .client(clients)
                .build();
    }
}
