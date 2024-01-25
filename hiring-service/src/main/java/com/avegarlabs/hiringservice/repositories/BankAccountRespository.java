package com.avegarlabs.hiringservice.repositories;

import com.avegarlabs.hiringservice.models.Bank;
import com.avegarlabs.hiringservice.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRespository extends JpaRepository<BankAccount, String> {

    public List<BankAccount> findAllByAccountOwner(String ownerId);

}
