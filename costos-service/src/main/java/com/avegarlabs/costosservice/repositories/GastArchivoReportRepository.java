package com.avegarlabs.costosservice.repositories;

import com.avegarlabs.costosservice.models.GastArchReport;
import com.avegarlabs.costosservice.models.GastIndReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GastArchivoReportRepository extends JpaRepository<GastArchReport, String> {

    List<GastArchReport> findByCreationDate(String date);
    List<GastArchReport> findByActivityId(String activityId);
}
