package com.avegarlabs.hiringservice.repositories;

import com.avegarlabs.hiringservice.models.SingContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SingContractRespository extends JpaRepository<SingContract, String> {

    List<SingContract> findByClientId(String clientId);

}
