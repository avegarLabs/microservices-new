package com.avegarlabs.facturationsalesservice.services;

import com.avegarlabs.facturationsalesservice.dto.CurrencyListItem;
import com.avegarlabs.facturationsalesservice.models.Currency;
import com.avegarlabs.facturationsalesservice.repositories.CurrencyRecpository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyService {

    private final CurrencyRecpository recpository;

    public List<CurrencyListItem> getAll(){
        return recpository.findAll().stream().map(this::mapCurrencyListItemToCurrencyModel).collect(Collectors.toList());
    }

    public Currency getCurrency(String currencyId){
        return recpository.findById(currencyId).get();
    }

    public CurrencyListItem mapCurrencyListItemToCurrencyModel(Currency currency){
        return CurrencyListItem.builder()
                .id(currency.getId())
                .name(currency.getName())
                .build();
    }

}
