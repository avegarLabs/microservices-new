package com.avegarlabs.hiringservice.repositories;

import com.avegarlabs.hiringservice.models.Contracts;
import com.avegarlabs.hiringservice.models.Currency;
import com.avegarlabs.hiringservice.models.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractsRespository extends JpaRepository<Contracts, String> {

    public  Contracts findByNumber(String number);

    public void  deleteByServiceRequest(ServiceRequest serviceRequest);

    public Contracts findByMoniker(String moniker);

}
