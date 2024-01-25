package com.avegarlabs.facturationsalesservice.repositories;

import com.avegarlabs.facturationsalesservice.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {

    List<Invoice> findAllByActivityId(String activityId);
}
