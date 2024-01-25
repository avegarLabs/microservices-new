package com.avegarlabs.costosservice.repositories;

import com.avegarlabs.costosservice.models.ActSalaryReport;
import com.avegarlabs.costosservice.models.GastIndReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GastIndReportRepository extends JpaRepository<GastIndReport, String> {

    List<GastIndReport> findByCreationDate(String date);
    List<GastIndReport> findByActivityId(String activityId);
}
