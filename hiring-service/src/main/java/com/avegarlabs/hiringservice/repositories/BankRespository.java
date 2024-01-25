package com.avegarlabs.hiringservice.repositories;

import com.avegarlabs.hiringservice.models.Activity;
import com.avegarlabs.hiringservice.models.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRespository extends JpaRepository<Bank, String> {

}
