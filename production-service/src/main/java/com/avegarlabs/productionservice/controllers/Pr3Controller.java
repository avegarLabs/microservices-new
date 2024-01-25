package com.avegarlabs.productionservice.controllers;

import com.avegarlabs.productionservice.dto.*;
import com.avegarlabs.productionservice.services.Pr3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pr3")
@CrossOrigin
@Slf4j
public class Pr3Controller {
    private final Pr3Service service;

    @GetMapping("/statics")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public Pr3StaticsListItem statics(){
        return service.getStatics();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Pr3ListItem> allList(){
        return service.getAllData();
    }


    @GetMapping("/month")
    @ResponseStatus(HttpStatus.OK)
    public List<Pr3ListItem> pr3ListInMonth(){
        return service.findInCurrentMonth();
    }

    @GetMapping("/resume")
    @ResponseStatus(HttpStatus.OK)
    public List<DepartamentStaticsListItem> resume(){
        return service.resumeByDepertament();
    }

    @GetMapping("/fixProd")
    @ResponseStatus(HttpStatus.OK)
    public List<FixePr3ListItem> fixePr3ListItemList(){
        return service.toFixProductionData();
    }


    @PutMapping("/fix/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void fixPr3Data(@PathVariable(value="id") String id, @RequestBody double value){
        service.fixValueData(id, value);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Pr3ListItem persistPr3(@RequestBody Pr3Model model){
        return service.createPr3(model);
    }



    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Pr3ListItem updatePr3Data(@PathVariable(value="id") String id, @RequestBody Pr3Model model){
        return service.updatePr3(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        service.deletePr3(id);
    }


    @GetMapping("/prod/activities")
    @ResponseStatus(HttpStatus.OK)
    public List<ResumeProdActList> resumeList(){
        return service.prodActListList();

    }






}
