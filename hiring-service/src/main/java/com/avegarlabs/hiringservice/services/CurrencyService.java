package com.avegarlabs.hiringservice.services;


import com.avegarlabs.hiringservice.dto.BankListItem;
import com.avegarlabs.hiringservice.dto.BankModel;
import com.avegarlabs.hiringservice.dto.CurrencyListItem;
import com.avegarlabs.hiringservice.dto.CurrencyModel;
import com.avegarlabs.hiringservice.models.Bank;
import com.avegarlabs.hiringservice.models.Currency;
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
public class CurrencyService {

    private final CurrencyRespository respository;


    public CurrencyListItem getCurrencyById(String id){
        Currency currency = respository.findById(id).get();
        return mapCurrencyListItemToBank(currency);
    }

    public List<CurrencyListItem> findAll() {
        return respository.findAll().stream().map(this::mapCurrencyListItemToBank).collect(Collectors.toList());
    }

    public CurrencyListItem addCurrency(CurrencyModel model) {
        Currency entity = mapCurrencyToCurrencyModel(model);
        respository.save(entity);
        return mapCurrencyListItemToBank(entity);
    }

    public CurrencyListItem updateCurrency(String id, CurrencyModel model) {
        Currency currency = respository.findById(id).get();
        Currency updateCurrency = updateCurrencyToCurrencyModel(currency, model);
        respository.save(updateCurrency);
        return mapCurrencyListItemToBank(updateCurrency);
    }

    public void deleteBank(String id) {
        respository.deleteById(id);
    }

    public CurrencyListItem mapCurrencyListItemToBank(Currency currency) {
        return CurrencyListItem.builder()
                .id(currency.getId())
                .name(currency.getName())
                .symbol(currency.getSymbol())
                .state(currency.getState())
                .build();
    }

    public Currency mapCurrencyToCurrencyModel(CurrencyModel model) {
        return Currency.builder()
                .name(model.getName())
                .state(true)
                .symbol(model.getSymbol())
                .build();
    }

    public Currency updateCurrencyToCurrencyModel(Currency entity, CurrencyModel model) {
        return Currency.builder()
                .id(entity.getId())
                .name(model.getName())
                .symbol(model.getSymbol())
                .state(model.getState())
                .build();
    }
}
