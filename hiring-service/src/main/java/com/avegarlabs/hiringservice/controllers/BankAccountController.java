package com.avegarlabs.hiringservice.controllers;

import com.avegarlabs.hiringservice.dto.BankAccountListItem;
import com.avegarlabs.hiringservice.dto.BankAccountModel;
import com.avegarlabs.hiringservice.dto.BankListItem;
import com.avegarlabs.hiringservice.dto.BankModel;
import com.avegarlabs.hiringservice.services.BankAccountService;
import com.avegarlabs.hiringservice.services.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring/bankAccount")
@CrossOrigin
public class BankAccountController {
    private final BankAccountService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<BankAccountListItem> allList(){
        return service.findAll();
    }


    @GetMapping("/owner/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<BankAccountListItem> getOwnerAccounts(@PathVariable(value="id") String id){
        return service.getByOwnerId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public BankAccountListItem persistClient(@RequestBody BankAccountModel model){
        return service.addBank(model);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BankAccountListItem updateBankData(@PathVariable(value="id") String id, @RequestBody BankAccountModel model){
        return service.updateBankAccount(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        service.deleteBankAccount(id);
    }

}
