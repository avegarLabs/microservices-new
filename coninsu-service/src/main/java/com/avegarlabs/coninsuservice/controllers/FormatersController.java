package com.avegarlabs.coninsuservice.controllers;

import com.avegarlabs.coninsuservice.dto.ConinsuListItem;
import com.avegarlabs.coninsuservice.dto.ConinsuModel;
import com.avegarlabs.coninsuservice.dto.FormaterListItem;
import com.avegarlabs.coninsuservice.dto.FormaterModel;
import com.avegarlabs.coninsuservice.services.FormaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/formatter")
@CrossOrigin
public class FormatersController {
    private final FormaterService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public FormaterListItem persistFormater(@RequestBody FormaterModel model){
        return service.saveFormater(model);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FormaterListItem> formatterList(){
        return service.getAllFormater();
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FormaterListItem updateFormaterData(@PathVariable(value="id") String id, @RequestBody FormaterModel model){
        return service.updateFormater(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        service.deleteFormater(id);
    }
}
