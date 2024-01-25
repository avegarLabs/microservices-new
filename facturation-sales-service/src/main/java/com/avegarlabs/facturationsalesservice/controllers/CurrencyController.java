package com.avegarlabs.facturationsalesservice.controllers;

import com.avegarlabs.facturationsalesservice.dto.CurrencyListItem;
import com.avegarlabs.facturationsalesservice.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/currency")
@CrossOrigin
public class CurrencyController {

    private final CurrencyService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<CurrencyListItem> currencyList(){
        return service.getAll();
    }
}
