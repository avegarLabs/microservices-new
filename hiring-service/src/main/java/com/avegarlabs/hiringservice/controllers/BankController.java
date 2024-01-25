package com.avegarlabs.hiringservice.controllers;

import com.avegarlabs.hiringservice.dto.BankListItem;
import com.avegarlabs.hiringservice.dto.BankModel;
import com.avegarlabs.hiringservice.dto.ClientListItem;
import com.avegarlabs.hiringservice.dto.ClientModel;
import com.avegarlabs.hiringservice.services.BankService;
import com.avegarlabs.hiringservice.services.ClientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring/bank")
@CrossOrigin
public class BankController {
    private final BankService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<BankListItem> allList(){
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public BankListItem persistClient(@RequestBody BankModel model){
        return service.addBank(model);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BankListItem updateBankData(@PathVariable(value="id") String id, @RequestBody BankModel model){
        return service.updateBank(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        service.deleteBank(id);
    }

}
