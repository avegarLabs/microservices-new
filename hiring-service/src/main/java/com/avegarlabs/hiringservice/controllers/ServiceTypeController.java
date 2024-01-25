package com.avegarlabs.hiringservice.controllers;

import com.avegarlabs.hiringservice.dto.ServiceTypeListItem;
import com.avegarlabs.hiringservice.dto.ServiceTypeModel;
import com.avegarlabs.hiringservice.services.ServiceTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring/serviceType")
@CrossOrigin
public class ServiceTypeController {
    private final ServiceTypeService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<ServiceTypeListItem> allList(){
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ServiceTypeListItem persistService(@RequestBody ServiceTypeModel model){
        return service.addService(model);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceTypeListItem updateServiceData(@PathVariable(value="id") String id, @RequestBody ServiceTypeModel model){
        return service.updateService(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        service.deleteType(id);
    }

}
