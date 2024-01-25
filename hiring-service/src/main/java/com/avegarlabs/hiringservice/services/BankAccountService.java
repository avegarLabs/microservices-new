package com.avegarlabs.hiringservice.services;


import com.avegarlabs.hiringservice.dto.*;
import com.avegarlabs.hiringservice.models.Bank;
import com.avegarlabs.hiringservice.models.BankAccount;
import com.avegarlabs.hiringservice.models.Clients;
import com.avegarlabs.hiringservice.models.Currency;
import com.avegarlabs.hiringservice.repositories.BankAccountRespository;
import com.avegarlabs.hiringservice.repositories.BankRespository;
import com.avegarlabs.hiringservice.repositories.CurrencyRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankAccountService {

    private final BankService bankService;
    private final BankRespository bankRespository;
    private final BankAccountRespository respository;
    private final CurrencyService currencyService;
    private final CurrencyRespository currencyRespository;
    private final ClientsService clientsService;


    public List<BankAccountListItem> getByOwnerId(String id){
       List<BankAccount> list = respository.findAllByAccountOwner(id);
        return list.stream().map(this::mapBankAccountListItemToBank).toList();
    }


    public List<BankAccountListItem> findAll() {
        return respository.findAll().stream().map(this::mapBankAccountListItemToBank).collect(Collectors.toList());
    }

    public BankAccountListItem addBank(BankAccountModel model) {
        BankAccount entity = mapBankAccountToBankAccountModel(model);
        respository.save(entity);
        return mapBankAccountListItemToBank(entity);
    }

    public BankAccountListItem updateBankAccount(String id, BankAccountModel model) {
        BankAccount bank = respository.findById(id).get();
        BankAccount updateBankAccont = updateBankToBankModel(bank, model);
        respository.save(updateBankAccont);
        return mapBankAccountListItemToBank(updateBankAccont);
    }

    public void deleteBankAccount(String id) {
        respository.deleteById(id);
    }

    public BankAccountListItem mapBankAccountListItemToBank(BankAccount bank) {
        BankListItem bankListItem = bankService.findBankbyId(bank.getBank().getId());
        CurrencyListItem currencyListItem = currencyService.getCurrencyById(bank.getCurrency().getId());
        return BankAccountListItem.builder()
                .id(bank.getId())
                .accountNumber(bank.getAccountNumber())
                .account(bank.getAccount())
                .license(bank.getLicense())
                .register(bank.getRegister())
                .accountOwner(bank.getAccountOwner())
                .state(bank.getState())
                .bank(bankListItem)
                .currency(currencyListItem)
                .build();
    }

    public BankAccount mapBankAccountToBankAccountModel(BankAccountModel bank) {
        Bank entBank = bankRespository.findById(bank.getBankId()).get();
        System.out.println(entBank.getName());
        Currency entCurrency = currencyRespository.findById(bank.getCurrencyId()).get();
        return BankAccount.builder()
                .accountNumber(bank.getAccountNumber())
                .account(bank.getAccount())
                .license(bank.getLicense())
                .register(bank.getRegister())
                .accountOwner(bank.getAccountOwner())
                .state(bank.getState())
                .bank(entBank)
                .currency(entCurrency)
                .build();
    }

    public BankAccount updateBankToBankModel(BankAccount entity, BankAccountModel model) {
        Bank entBank = bankRespository.findById(model.getBankId()).get();
        Currency entCurrency = currencyRespository.findById(model.getCurrencyId()).get();
        return BankAccount.builder()
                .id(entity.getId())
                .accountNumber(model.getAccountNumber())
                .account(model.getAccount())
                .license(model.getLicense())
                .register(model.getRegister())
                .accountOwner(model.getAccountOwner())
                .state(model.getState())
                .bank(entBank)
                .currency(entCurrency)
                .build();
    }
}
