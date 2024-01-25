package com.avegarlabs.hiringservice.controllers;


import com.avegarlabs.hiringservice.dto.ContractsControlResponse;
import com.avegarlabs.hiringservice.dto.GroupStaticsModel;
import com.avegarlabs.hiringservice.services.BasicStaticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring/static")
@CrossOrigin
public class BasicStaticController {

    private final BasicStaticService service;

    @GetMapping("/departaments")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<GroupStaticsModel> byDepartaments() {
        return service.groupByDepartament();
    }


    @GetMapping("/typesServices")
    @ResponseStatus(HttpStatus.OK)
    public List<GroupStaticsModel> byServices() {
        return service.groupByServicesType();
    }

    @GetMapping("/control")
    @ResponseStatus(HttpStatus.OK)
    public List<ContractsControlResponse> contractControl() {
        return service.getControlResponse();
    }


}
