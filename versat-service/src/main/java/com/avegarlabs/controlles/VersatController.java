package com.avegarlabs.controlles;

import com.avegarlabs.dto.DatNomListItem;
import com.avegarlabs.services.SqlServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/versat")
@CrossOrigin
public class VersatController {

    private final SqlServerService service;

    @GetMapping("/datNom")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<DatNomListItem> datNomListItems(){
        return service.getVERSATNomModuleData();
    }
}
