package com.avegarlabs.costosservice.repositories;

import com.avegarlabs.costosservice.models.ActSalaryReport;
import com.avegarlabs.costosservice.models.OthersConcepts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OthersConceptsRepository extends JpaRepository<OthersConcepts, String> {

    List<OthersConcepts> findByCreationDate(String date);
    List<OthersConcepts> findByActivityId(String activityId);
}
