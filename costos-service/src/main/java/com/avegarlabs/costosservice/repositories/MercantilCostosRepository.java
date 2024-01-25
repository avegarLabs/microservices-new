package com.avegarlabs.costosservice.repositories;

import com.avegarlabs.costosservice.models.MercantilCostos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MercantilCostosRepository extends JpaRepository<MercantilCostos, String> {

    List<MercantilCostos> findByCreationDate(String date);
    List<MercantilCostos> findByActivityId(String activityId);
}
