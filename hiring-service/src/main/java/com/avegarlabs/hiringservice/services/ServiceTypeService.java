package com.avegarlabs.hiringservice.services;


import com.avegarlabs.hiringservice.dto.ServiceTypeListItem;
import com.avegarlabs.hiringservice.dto.ServiceTypeModel;
import com.avegarlabs.hiringservice.models.ServiceType;
import com.avegarlabs.hiringservice.repositories.ServiceTypeRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceTypeService {

    private final ServiceTypeRespository respository;

    public ServiceType findServicebyId(String id){
        return respository.findById(id).get();
    }

    public List<ServiceTypeListItem> findAll() {
        return respository.findAll().stream().map(this::mapServiceTypeListItemToServiceType).collect(Collectors.toList());
    }

    public ServiceTypeListItem addService(ServiceTypeModel model) {
        ServiceType entity = mapServiceTypeToModel(model);
        respository.save(entity);
        return mapServiceTypeListItemToServiceType(entity);
    }

    public ServiceTypeListItem updateService(String id, ServiceTypeModel model) {
        ServiceType type = respository.findById(id).get();
        ServiceType updateType = updateServiceToModel(type, model);
        respository.save(updateType);
        return mapServiceTypeListItemToServiceType(type);
    }

    public void deleteType(String id) {
        respository.deleteById(id);
    }

    public ServiceTypeListItem mapServiceTypeListItemToServiceType(ServiceType serviceType) {
        return ServiceTypeListItem.builder()
                .id(serviceType.getId())
                .code(serviceType.getCode())
                .description(serviceType.getDescription())
                .build();
    }

    public ServiceType mapServiceTypeToModel(ServiceTypeModel model) {
        return ServiceType.builder()
                .code(model.getCode())
                .description(model.getDescription())
                .build();
    }

    public ServiceType updateServiceToModel(ServiceType entity, ServiceTypeModel model) {
        return ServiceType.builder()
                .id(entity.getId())
                .code(model.getCode())
                .description(model.getDescription())
                .build();
    }
}
