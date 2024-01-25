package com.avegarlabs.hiringservice.services;


import com.avegarlabs.hiringservice.dto.BankListItem;
import com.avegarlabs.hiringservice.dto.BankModel;
import com.avegarlabs.hiringservice.models.Bank;
import com.avegarlabs.hiringservice.repositories.BankRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankService {

    private final BankRespository respository;

    public BankListItem findBankbyId(String id){
        Bank bank = respository.findById(id).get();
        return mapBankListItemToBank(bank);
    }

    public List<BankListItem> findAll() {
        return respository.findAll().stream().map(this::mapBankListItemToBank).collect(Collectors.toList());
    }

    public BankListItem addBank(BankModel model) {
        Bank entity = mapBankToBankModel(model);
        respository.save(entity);
        return mapBankListItemToBank(entity);
    }

    public BankListItem updateBank(String id, BankModel model) {
        Bank bank = respository.findById(id).get();
        Bank updateBank = updateBankToBankModel(bank, model);
        respository.save(updateBank);
        return mapBankListItemToBank(updateBank);
    }

    public void deleteBank(String id) {
        respository.deleteById(id);
    }

    public BankListItem mapBankListItemToBank(Bank bank) {
        return BankListItem.builder()
                .id(bank.getId())
                .code(bank.getCode())
                .name(bank.getName())
                .denomination(bank.getDenomination())
                .state(bank.getState())
                .build();
    }

    public Bank mapBankToBankModel(BankModel model) {
        return Bank.builder()
                .code(model.getCode())
                .name(model.getName())
                .denomination(model.getDenomination())
                .state(true)
                .build();
    }

    public Bank updateBankToBankModel(Bank entity, BankModel model) {
        return Bank.builder()
                .id(entity.getId())
                .code(model.getCode())
                .name(model.getName())
                .denomination(model.getDenomination())
                .state(model.getState())
                .build();
    }
}
