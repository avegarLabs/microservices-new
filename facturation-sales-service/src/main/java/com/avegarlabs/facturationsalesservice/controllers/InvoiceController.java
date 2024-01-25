package com.avegarlabs.facturationsalesservice.controllers;


import com.avegarlabs.facturationsalesservice.dto.InvSaleStaticsListItem;
import com.avegarlabs.facturationsalesservice.dto.InvoiceListItem;
import com.avegarlabs.facturationsalesservice.dto.InvoiceModel;
import com.avegarlabs.facturationsalesservice.dto.SalePostModel;
import com.avegarlabs.facturationsalesservice.services.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invoice")
@CrossOrigin
public class InvoiceController {
    private final InvoiceService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public InvoiceListItem persistInvoice(@RequestBody InvoiceModel model){
        return service.addInvoice(model);
    }


    @GetMapping("/static")
    @ResponseStatus(HttpStatus.OK)
    public InvSaleStaticsListItem getStatics(){
        return service.getStatics();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceListItem> invoiceList(){
        return service.getAllToGlobal();
    }

    @GetMapping("/month")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceListItem> invoiceListInMonth(){
        return service.findInCurrentMonth();
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceListItem updateInvoiceData(@PathVariable(value="id") String id, @RequestBody InvoiceModel model){
        return service.updateInvoice(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceListItem remove(@PathVariable(value="id") String id){
         return service.deleteInvoice(id);

    }

}
