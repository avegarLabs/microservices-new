package com.avegarlabs.hiringservice.controllers;

import com.avegarlabs.hiringservice.dto.*;
import com.avegarlabs.hiringservice.services.HrUtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
@CrossOrigin
@Slf4j
public class HrUtilController {

    private final HrUtilService utilService;

    @GetMapping("/deptos")
    @RolesAllowed("backend-admin")
    @ResponseStatus(HttpStatus.OK)
    public List<DepartamentListItem>  departamentList() {
        return utilService.getDepartamentList().getDepartamentos();
    }

    @GetMapping("/types")
    @ResponseStatus(HttpStatus.OK)
    public List<ActTypeListItem>  typeListItemsList() {
        return utilService.getTypesList().getTipos();
    }

    @GetMapping("/personal")
    @ResponseStatus(HttpStatus.OK)
    public List<WorkerListItem>  typeWorkersItemsList() {
        return utilService.getPersonalList().getPersonal();
    }

    @GetMapping("/charges")
    @ResponseStatus(HttpStatus.OK)
    public List<ChargesListItem> chargesListItemList() {
        return utilService.getChargeslList().getCargos();
    }
}
