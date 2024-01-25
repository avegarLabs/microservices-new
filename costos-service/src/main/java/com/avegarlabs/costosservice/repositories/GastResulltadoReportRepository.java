package com.avegarlabs.costosservice.repositories;

import com.avegarlabs.costosservice.models.GastIndReport;
import com.avegarlabs.costosservice.models.GastResultadoReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GastResulltadoReportRepository extends JpaRepository<GastResultadoReport, String> {

    List<GastResultadoReport> findByCreationDate(String date);

    GastResultadoReport findByActivityIdAndCreationDate(String activityId, String date);
}
