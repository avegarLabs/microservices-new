package com.avegarlabs.hiringservice.repositories;

import com.avegarlabs.hiringservice.models.ActivityStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityStaffRespository extends JpaRepository<ActivityStaff, String> {
    List<ActivityStaff> findAllByActivityId(String activityId);
}
