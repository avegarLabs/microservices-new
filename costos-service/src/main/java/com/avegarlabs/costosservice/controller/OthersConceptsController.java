package com.avegarlabs.costosservice.controller;

import com.avegarlabs.costosservice.dto.OtherConceptsListItem;
import com.avegarlabs.costosservice.dto.OtherConceptsModel;
import com.avegarlabs.costosservice.services.OthersConceptsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/concepts")
@CrossOrigin
public class OthersConceptsController {

    private final OthersConceptsService service;

    @GetMapping("/month")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<OtherConceptsListItem> getAll(){
        return service.readOtherOConcepts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public OtherConceptsListItem persistConcepts(@RequestBody OtherConceptsModel model){
        return service.createOtherConcepts(model);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OtherConceptsListItem updateConcepts(@PathVariable(value="id") String id, @RequestBody OtherConceptsModel model){
        return service.updateOtherConcepts(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        service.deleteConcepts(id);
    }

}
