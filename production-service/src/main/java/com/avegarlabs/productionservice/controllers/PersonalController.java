package com.avegarlabs.productionservice.controllers;

import com.avegarlabs.productionservice.dto.WorkerListItem;
import com.avegarlabs.productionservice.services.HrUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/personal")
@CrossOrigin
public class PersonalController {

    private final HrUtilService service;

    @GetMapping
    @RolesAllowed("backend-admin")
    @ResponseStatus(HttpStatus.OK)
    public List<WorkerListItem> departamentList() {
        return service.getPersonalList().getPersonal();
    }
}
