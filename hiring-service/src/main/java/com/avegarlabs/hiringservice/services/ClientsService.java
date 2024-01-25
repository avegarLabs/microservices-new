package com.avegarlabs.hiringservice.services;


import com.avegarlabs.hiringservice.dto.ClientListItem;
import com.avegarlabs.hiringservice.dto.ClientModel;
import com.avegarlabs.hiringservice.models.Clients;
import com.avegarlabs.hiringservice.repositories.ClientsRespository;
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
public class ClientsService {

    private final ClientsRespository respository;
    private final DateData dateData;
    private final CreateMoniker createMoniker;

    public List<ClientListItem> findAll() {
        return respository.findAll().stream().map(this::mapClientListItemToClient).collect(Collectors.toList());
    }

    public Clients getClientById(String id){
        return respository.findById(id).get();
    }

    public ClientListItem getClientListItemById(String id){
        Clients clients = respository.findById(id).get();
        return mapClientListItemToClient(clients);
    }

    public ClientListItem getClientByMoniker(String moniker){
        Clients clients = respository.findByMoniker(moniker);
        return mapClientListItemToClient(clients);
    }

    public ClientListItem addClient(ClientModel model) {
        Clients client = mapClientToClientModel(model);
        respository.save(client);
        return mapClientListItemToClient(client);
    }

    public ClientListItem updateClient(String id, ClientModel model) {
        Clients clientData = respository.findById(id).get();
        Clients entity = updateClientDataFromModel(clientData, model);
        respository.save(entity);
        return mapClientListItemToClient(entity);
    }

    public void deleteClient(String id) {
        respository.deleteById(id);
    }

    public ClientListItem mapClientListItemToClient(Clients client) {
        return ClientListItem.builder()
                .id(client.getId())
                .name(client.getName())
                .reupCode(client.getReupCode())
                .shareCode(client.getShareCode())
                .acronym(client.getAcronym())
                .constResolution(client.getConstResolution())
                .phone(client.getPhone())
                .fax(client.getFax())
                .address(client.getAddress())
                .nit(client.getNit())
                .enterpriseGroup(client.getEnterpriseGroup())
                .email(client.getEmail())
                .organization(client.getOrganization())
                .moniker(client.getMoniker())
                .build();
    }

    public Clients mapClientToClientModel(ClientModel client) {
        String moniker = createMoniker.createMoniker(client.getName());
         return Clients.builder()
                 .name(client.getName())
                 .reupCode(client.getReupCode())
                 .shareCode(client.getShareCode())
                 .acronym(client.getAcronym())
                 .constResolution(client.getConstResolution())
                 .phone(client.getPhone())
                 .fax(client.getFax())
                 .address(client.getAddress())
                 .nit(client.getNit())
                 .enterpriseGroup(client.getEnterpriseGroup())
                 .email(client.getEmail())
                 .organization(client.getOrganization())
                 .creationDate(dateData.currentMonth())
                 .lastUpdateTime(dateData.today())
                 .moniker(moniker)
                .build();
    }

    public Clients updateClientDataFromModel(Clients entity, ClientModel client) {
        String moniker = createMoniker.createMoniker(client.getName());
        return Clients.builder()
                .id(entity.getId())
                .name(client.getName())
                .reupCode(client.getReupCode())
                .shareCode(client.getShareCode())
                .acronym(client.getAcronym())
                .constResolution(client.getConstResolution())
                .phone(client.getPhone())
                .fax(client.getFax())
                .address(client.getAddress())
                .nit(client.getNit())
                .enterpriseGroup(client.getEnterpriseGroup())
                .email(client.getEmail())
                .organization(client.getOrganization())
                .creationDate(entity.creationDate)
                .lastUpdateTime(dateData.today())
                .moniker(moniker)
                .build();
    }
}
