package com.avegarlabs.facturationsalesservice.repositories;

import com.avegarlabs.facturationsalesservice.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, String> {

    List<Sale> findSaleByActivityId(String activityId);
}
