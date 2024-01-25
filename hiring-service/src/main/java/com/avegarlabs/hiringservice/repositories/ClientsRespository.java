package com.avegarlabs.hiringservice.repositories;

import com.avegarlabs.hiringservice.models.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRespository extends JpaRepository<Clients, String> {

    public Clients findByMoniker(String moniker);

}
