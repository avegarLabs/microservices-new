package com.avegarlabs.hiringservice.controllers;



import com.avegarlabs.hiringservice.dto.WorkOrderListItem;
import com.avegarlabs.hiringservice.dto.WorkOrderModel;
import com.avegarlabs.hiringservice.services.WorkOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring/order")
@CrossOrigin
public class WorkOrderController {
    private final WorkOrderService service;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<WorkOrderListItem> findAll(){
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public WorkOrderListItem persisOrder(@RequestBody WorkOrderModel model){
        return service.addWorkOrder(model);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkOrderListItem updateOrderDataData(@PathVariable(value="id") String id, @RequestBody WorkOrderModel model){
        return service.updateWorkOrder(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        service.deleteOrder(id);

    }

}
