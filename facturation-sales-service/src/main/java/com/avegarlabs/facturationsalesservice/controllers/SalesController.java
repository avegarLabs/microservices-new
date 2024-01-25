package com.avegarlabs.facturationsalesservice.controllers;


import com.avegarlabs.facturationsalesservice.dto.*;
import com.avegarlabs.facturationsalesservice.services.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sale")
@CrossOrigin
public class SalesController {
    private final SalesService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public SaleListItem persistInvoice(@RequestBody SaleModel model){
        return service.addSale(model);
    }

    @GetMapping("/month")
    @ResponseStatus(HttpStatus.OK)
    public List<SaleListItem> saleListInMonth(){
        return service.findInCurrentMonth();
    }

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.OK)
    public String addSaleS(@RequestBody SalePostModel postModel){
        return  service.saveSaleInBatch(postModel);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SaleListItem> invoiceList(){
        return service.getAllToGlobal();
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<SaleListItem> allSales(){
        return service.getAll();
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SaleListItem updateSaleData(@PathVariable(value="id") String id, @RequestBody SaleModel model){
        return service.updateSale(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        service.deleteSale(id);
    }

    @GetMapping("/resume")
    @ResponseStatus(HttpStatus.OK)
    public List<DepartamentStaticsListItem> resume(){
        return service.getSaleByDepartaments();
    }

    @GetMapping("/resumeByService")
    @ResponseStatus(HttpStatus.OK)
    public List<ByServicesStaticsListItem> resumeByService(){
        return service.getResumeByServices();
    }

    @GetMapping("/by/activities")
    @ResponseStatus(HttpStatus.OK)
    public List<ResumeSaleList> resumeByActivities(){
        return service.getResumeSale();
    }


}
