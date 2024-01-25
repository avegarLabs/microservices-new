package com.avegarlabs.hiringservice.controllers;

import com.avegarlabs.hiringservice.dto.ServiceRequestListItem;
import com.avegarlabs.hiringservice.dto.ServiceRequestModel;
import com.avegarlabs.hiringservice.services.ServiceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring/serviceRequest")
@CrossOrigin
public class ServiceRequestController {
    private final ServiceRequestService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<ServiceRequestListItem> allList(){
        return service.findAll();
    }

    @GetMapping("/{moniker}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceRequestListItem readServiceRequestByMoniker(@PathVariable(value = "moniker") String moniker){
        return service.getRequestByMoniker(moniker);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ServiceRequestListItem persistRequest(@RequestBody ServiceRequestModel model){
        return service.addService(model);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceRequestListItem updateRequestData(@PathVariable(value="id") String id, @RequestBody ServiceRequestModel model){
        return service.updateService(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        service.deleteServiceRequest(id);

    }

}
