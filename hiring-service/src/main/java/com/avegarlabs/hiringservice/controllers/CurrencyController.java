package com.avegarlabs.hiringservice.controllers;

import com.avegarlabs.hiringservice.dto.BankListItem;
import com.avegarlabs.hiringservice.dto.BankModel;
import com.avegarlabs.hiringservice.dto.CurrencyListItem;
import com.avegarlabs.hiringservice.dto.CurrencyModel;
import com.avegarlabs.hiringservice.services.BankService;
import com.avegarlabs.hiringservice.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring/currency")
@CrossOrigin
public class CurrencyController {
    private final CurrencyService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<CurrencyListItem> allList() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CurrencyListItem persistCurrency(@RequestBody CurrencyModel model) {
        return service.addCurrency(model);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CurrencyListItem updateCurrencyData(@PathVariable(value = "id") String id, @RequestBody CurrencyModel model) {
        return service.updateCurrency(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value = "id") String id) {
        service.deleteBank(id);
    }

}
