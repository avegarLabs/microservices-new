package com.avegarlabs.coninsuservice.controllers;

import com.avegarlabs.coninsuservice.dto.ConinsuListItem;
import com.avegarlabs.coninsuservice.dto.ConinsuModel;
import com.avegarlabs.coninsuservice.dto.ConinsuStaticsListItem;
import com.avegarlabs.coninsuservice.services.ConinsuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coninsu")
@CrossOrigin
public class ConinsuController {

    private final ConinsuService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public ConinsuListItem persistConinsu(@RequestBody ConinsuModel model){
        return service.saveConinsu(model);
    }

    @GetMapping("/statics")
    @ResponseStatus(HttpStatus.OK)
    public ConinsuStaticsListItem getStatics(){
        return service.getStatics();
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ConinsuListItem> coninsuList(){
        return service.getAll();
    }

    @GetMapping("/month")
    @ResponseStatus(HttpStatus.OK)
    public List<ConinsuListItem> coninsuListInMonth(){
        return service.findInCurrentMonth();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConinsuListItem updateConinsuData(@PathVariable(value="id") String id, @RequestBody ConinsuModel model){
        return service.updateConinsu(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        service.deleteConinsu(id);
    }

}
