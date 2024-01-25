package com.avegarlabs.hiringservice.repositories;

import com.avegarlabs.hiringservice.models.Bank;
import com.avegarlabs.hiringservice.models.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeRespository extends JpaRepository<ServiceType, String> {

}
