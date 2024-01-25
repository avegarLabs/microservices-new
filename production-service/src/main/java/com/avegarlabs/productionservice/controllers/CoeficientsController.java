package com.avegarlabs.productionservice.controllers;

import com.avegarlabs.productionservice.dto.CoeficientListItem;
import com.avegarlabs.productionservice.dto.CoeficientModel;
import com.avegarlabs.productionservice.services.CoeficientsServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coeficient")
@CrossOrigin
public class CoeficientsController {
    private final CoeficientsServices services;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public CoeficientListItem allList(){
        return services.getCoeficientes().get(0);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CoeficientListItem persistCoeficient(@RequestBody CoeficientModel model){
        return services.persisteCoeficient(model);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CoeficientListItem updateCoeficientsData(@PathVariable(value="id") String id, @RequestBody CoeficientModel model){
        return services.updateCoeficients(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        services.removeCoeficiente(id);
    }
}
