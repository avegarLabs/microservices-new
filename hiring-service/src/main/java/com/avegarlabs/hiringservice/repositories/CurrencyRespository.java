package com.avegarlabs.hiringservice.repositories;

import com.avegarlabs.hiringservice.models.Bank;
import com.avegarlabs.hiringservice.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRespository extends JpaRepository<Currency, String> {

}
