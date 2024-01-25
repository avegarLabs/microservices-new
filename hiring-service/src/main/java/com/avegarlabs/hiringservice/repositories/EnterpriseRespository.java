package com.avegarlabs.hiringservice.repositories;

import com.avegarlabs.hiringservice.models.Clients;
import com.avegarlabs.hiringservice.models.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseRespository extends JpaRepository<Enterprise, String> {

    public Enterprise findByMoniker(String moniker);

}
