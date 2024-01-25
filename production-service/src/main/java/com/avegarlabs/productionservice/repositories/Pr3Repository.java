package com.avegarlabs.productionservice.repositories;

import com.avegarlabs.productionservice.models.Pr3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Pr3Repository extends JpaRepository<Pr3, String> {
}
