package com.avegarlabs.hiringservice.repositories;

import com.avegarlabs.hiringservice.models.Project;
import com.avegarlabs.hiringservice.models.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, String> {

    public void deleteByServiceRequest(ServiceRequest serviceRequest);

    Project findProjectByMoniker(String moniker);
}
