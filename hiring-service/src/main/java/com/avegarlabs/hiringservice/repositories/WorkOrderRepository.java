package com.avegarlabs.hiringservice.repositories;

import com.avegarlabs.hiringservice.models.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, String> {
    List<WorkOrder> findWorkOrdersByProjectId(String id);
}
