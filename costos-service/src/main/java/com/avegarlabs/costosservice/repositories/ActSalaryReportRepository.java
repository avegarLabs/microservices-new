package com.avegarlabs.costosservice.repositories;

import com.avegarlabs.costosservice.models.ActSalaryReport;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActSalaryReportRepository extends JpaRepository<ActSalaryReport, String> {

    List<ActSalaryReport> findByCreationDate(String date);
    List<ActSalaryReport> getActSalaryReportsByActivityId(String activityid);

    ActSalaryReport findByActivityIdAndCreationDate(String actId, String date);


}
