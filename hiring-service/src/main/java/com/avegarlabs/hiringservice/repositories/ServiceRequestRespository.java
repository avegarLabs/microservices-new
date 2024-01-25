package com.avegarlabs.hiringservice.repositories;

import com.avegarlabs.hiringservice.models.ServiceRequest;
import com.avegarlabs.hiringservice.models.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRequestRespository extends JpaRepository<ServiceRequest, String> {

    public ServiceRequest findByMoniker(String moniker);

    List<ServiceRequest> searchServiceRequestByClientId(String clientId);
    List<ServiceRequest> searchServiceRequestByServiceTypeId(String serviceId);
    List<ServiceRequest> searchServiceRequestByDirectionId(int directionId);

}
