package com.avegarlabs.productionservice.repositories;

import com.avegarlabs.productionservice.models.Coeficients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoeficientsRepository extends JpaRepository<Coeficients, String> {
}
