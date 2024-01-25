package com.avegarlabs.hiringservice.controllers;

import com.avegarlabs.hiringservice.dto.ClientListItem;
import com.avegarlabs.hiringservice.dto.ClientModel;
import com.avegarlabs.hiringservice.services.ClientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring/clients")
@CrossOrigin
public class ClientsController {
    private final ClientsService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<ClientListItem> allList(){
        return service.findAll();
    }

    @GetMapping("/{moniker}")
    @ResponseStatus(HttpStatus.OK)
    public ClientListItem readClientByMoniker(@PathVariable(value = "moniker") String moniker){
        return service.getClientByMoniker(moniker);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ClientListItem persistClient(@RequestBody ClientModel model){
        return service.addClient(model);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientListItem updateClientData(@PathVariable(value="id") String id, @RequestBody ClientModel model){
        return service.updateClient(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        service.deleteClient(id);

    }

}
