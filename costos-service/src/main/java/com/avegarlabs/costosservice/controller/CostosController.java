package com.avegarlabs.costosservice.controller;



import com.avegarlabs.costosservice.dto.*;
import com.avegarlabs.costosservice.models.InProcessCostos;
import com.avegarlabs.costosservice.services.CostosServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/costos")
@CrossOrigin
public class CostosController {

    private final CostosServices services;

    @GetMapping("/salaryReport")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<ActSalaryListItem> readActSalaryReport(){
        return services.getSalaryResume();
    }

    @GetMapping("/calcSalaryReport")
    @ResponseStatus(HttpStatus.OK)
    public List<ActSalaryListItem> createActSalaryReport(){
        return services.saveSalaryResume();
    }

    @GetMapping("/gtosInd")
    @ResponseStatus(HttpStatus.OK)
    public List<GastIndListItem> readGtosIndReport(){
        return services.getGastIndResume();
    }

    @GetMapping("/calcGstoInd/{value}")
    @ResponseStatus(HttpStatus.OK)
    public List<GastIndListItem> createGstoIndReport(@PathVariable(value="value") double value){
        return services.saveGastIndReport(value);
    }

    @GetMapping("/gtosResultado")
    @ResponseStatus(HttpStatus.OK)
    public List<GastResultadoListItem> readGtosResultados(){
        return services.getGastResultadoResume();
    }

    @GetMapping("/calcGstoResult/{value}")
    @ResponseStatus(HttpStatus.OK)
    public List<GastResultadoListItem> createGstoResultReport(@PathVariable(value="value") double value){
        return services.saveGastResultado(value);
    }

    @GetMapping("/gtosArchivo")
    @ResponseStatus(HttpStatus.OK)
    public List<GastArchivoListItem> readGtosArchivo(){
        return services.getGastArchivoResume();
    }

    @GetMapping("/calcGstoArch/{value}")
    @ResponseStatus(HttpStatus.OK)
    public List<GastArchivoListItem> createGstoArchReport(@PathVariable(value="value") double value){
        return services.saveGastArchivo(value);
    }

    @GetMapping("/statics")
    @ResponseStatus(HttpStatus.OK)
    public ServicesTypesStaticsItem readStatics(){
        return services.getStatics();
    }


    @GetMapping("/inProcessCosto")
    @ResponseStatus(HttpStatus.OK)
    public List<InProcesCostosListItem> readInProcessCostos(){
        return services.getCostosInProcess();
    }

    @GetMapping("/calinProcessCosto")
    @ResponseStatus(HttpStatus.OK)
    public List<InProcesCostosListItem> createInProcessCostos(){
        return services.saveCostosInProcess();
    }

    @GetMapping("/mercCostos")
    @ResponseStatus(HttpStatus.OK)
    public List<SalesCostosListItem> readMercantilCostos(){
        return services.getMercantilCostos();
    }

    @GetMapping("/calMercCostos")
    @ResponseStatus(HttpStatus.OK)
    public List<SalesCostosListItem> createMercantilCostos(){
        return services.saveMercantilCostos();
    }



}
