package com.avegarlabs.hiringservice.controllers;

import com.avegarlabs.hiringservice.dto.EnterpriseListItem;
import com.avegarlabs.hiringservice.dto.EnterpriseModel;
import com.avegarlabs.hiringservice.services.EnterpriseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring/enterprise")
@CrossOrigin
public class EnterpriseController {
    private final EnterpriseService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<EnterpriseListItem> allList(){
        return service.findAll();
    }

    @GetMapping("/{moniker}")
    @ResponseStatus(HttpStatus.OK)
    public EnterpriseListItem readEnterpriseByMoniker(@PathVariable(value = "moniker") String moniker){
        return service.getEnterpriseByMoniker(moniker);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public EnterpriseListItem persistEnterprise(@RequestBody EnterpriseModel model){
        return service.addEnterprise(model);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EnterpriseListItem updateEnterpriseData(@PathVariable(value="id") String id, @RequestBody EnterpriseModel model){
        return service.updateEnterprise(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        service.deleteEnterprise(id);

    }

}
