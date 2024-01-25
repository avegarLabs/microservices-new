package com.avegarlabs.facturationsalesservice.repositories;

import com.avegarlabs.facturationsalesservice.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRecpository extends JpaRepository<Currency, String> {
}
