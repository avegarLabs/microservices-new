package com.avegarlabs.hiringservice.controllers;

import com.avegarlabs.hiringservice.dto.SingContractListItem;
import com.avegarlabs.hiringservice.dto.SingContractModel;
import com.avegarlabs.hiringservice.services.SingContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring/singContract")
@CrossOrigin
public class SingContractController {
    private final SingContractService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<SingContractListItem> allList(){
        return service.findAll();
    }


    @GetMapping("/client/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<SingContractListItem> getByClient(@PathVariable(value="id") String id){
        return service.getByClientId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public SingContractListItem persistSinger(@RequestBody SingContractModel model){
        return service.addSingContractk(model);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SingContractListItem updateBankData(@PathVariable(value="id") String id, @RequestBody SingContractModel model){
        return service.updateSingContract(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        service.deleteSingContract(id);
    }

}
