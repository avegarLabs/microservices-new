package com.avegarlabs.hiringservice.repositories;

import com.avegarlabs.hiringservice.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRespository extends JpaRepository<Activity, String> {
    List<Activity> findAllByOrderId(String orderId);
}
