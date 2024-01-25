package com.avegarlabs.hiringservice.controllers;

import com.avegarlabs.hiringservice.dto.ContractsListItem;
import com.avegarlabs.hiringservice.dto.ContractsModel;
import com.avegarlabs.hiringservice.services.ContratsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring/contracts")
@CrossOrigin
public class ContractsController {
    private final ContratsService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<ContractsListItem> allList(){
        return service.findAll();
    }

    @GetMapping("/{number}")
    @ResponseStatus(HttpStatus.OK)
    public ContractsListItem readClientByNumber(@PathVariable(value = "number") String number){
        return service.findContractsByNumber(number);
    }

    @GetMapping("/by/{moniker}")
    @ResponseStatus(HttpStatus.OK)
    public ContractsListItem readContractByMoniker(@PathVariable(value = "moniker") String moniker){
        return service.findContractsByMoniker(moniker);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ContractsListItem persistContracts(@RequestBody ContractsModel model){
        return service.addContracts(model);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractsListItem updateContractsData(@PathVariable(value="id") String id, @RequestBody ContractsModel model){
        return service.updateContrats(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        service.deleteContracts(id);

    }

}
