package com.avegarlabs.costosservice.repositories;

import com.avegarlabs.costosservice.models.GastIndReport;
import com.avegarlabs.costosservice.models.InProcessCostos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InProcessCostosRepository extends JpaRepository<InProcessCostos, String> {

    List<InProcessCostos> findByCreationDate(String date);
    List<InProcessCostos> findByActivityId(String acivityId);

}
