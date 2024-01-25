package com.avegarlabs.hiringservice.services;


import com.avegarlabs.hiringservice.dto.EnterpriseListItem;
import com.avegarlabs.hiringservice.dto.EnterpriseModel;
import com.avegarlabs.hiringservice.models.Enterprise;
import com.avegarlabs.hiringservice.repositories.EnterpriseRespository;
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
public class EnterpriseService {

    private final EnterpriseRespository respository;
    private final DateData dateData;
    private final CreateMoniker createMoniker;

    public List<EnterpriseListItem> findAll() {
        return respository.findAll().stream().map(this::mapEnterpriseListItemToEnterprise).collect(Collectors.toList());
    }

    public Enterprise getEnterpriseById(String id){
        return respository.findById(id).get();
    }

    public EnterpriseListItem getEnterpriseByMoniker(String moniker){
        Enterprise clients = respository.findByMoniker(moniker);
        return mapEnterpriseListItemToEnterprise(clients);
    }

    public EnterpriseListItem addEnterprise(EnterpriseModel model) {
        Enterprise client = mapEnterpriseToEnterpriseModel(model);
        respository.save(client);
        return mapEnterpriseListItemToEnterprise(client);
    }

    public EnterpriseListItem updateEnterprise(String id, EnterpriseModel model) {
        Enterprise clientData = respository.findById(id).get();
        Enterprise entity = updateEnterprisetDataFromModel(clientData, model);
        respository.save(entity);
        return mapEnterpriseListItemToEnterprise(entity);
    }

    public void deleteEnterprise(String id) {
        respository.deleteById(id);
    }

    public EnterpriseListItem mapEnterpriseListItemToEnterprise(Enterprise client) {
        return EnterpriseListItem.builder()
                .id(client.getId())
                .name(client.getName())
                .reupCode(client.getReupCode())
                .acronym(client.getAcronym())
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

    public Enterprise mapEnterpriseToEnterpriseModel(EnterpriseModel client) {
        String moniker = createMoniker.createMoniker(client.getName());
         return Enterprise.builder()
                 .name(client.getName())
                 .reupCode(client.getReupCode())
                 .acronym(client.getAcronym())
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

    public Enterprise updateEnterprisetDataFromModel(Enterprise entity, EnterpriseModel client) {
        String moniker = createMoniker.createMoniker(client.getName());
        return Enterprise.builder()
                .id(entity.getId())
                .name(client.getName())
                .reupCode(client.getReupCode())
                .acronym(client.getAcronym())
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
