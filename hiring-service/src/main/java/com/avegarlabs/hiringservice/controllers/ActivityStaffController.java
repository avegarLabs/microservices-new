package com.avegarlabs.hiringservice.controllers;

import com.avegarlabs.hiringservice.dto.ActivityStaffListItem;
import com.avegarlabs.hiringservice.dto.ActivityStaffModel;
import com.avegarlabs.hiringservice.dto.BankAccountListItem;
import com.avegarlabs.hiringservice.dto.BankAccountModel;
import com.avegarlabs.hiringservice.services.ActivityStaffService;
import com.avegarlabs.hiringservice.services.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring/activityStaff")
@CrossOrigin
public class ActivityStaffController {
    private final ActivityStaffService service;


    @GetMapping("/activity/{id}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<ActivityStaffListItem> getStaffInAct(@PathVariable(value="id") String id){
        return service.getAll(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ActivityStaffListItem persistStaffMember(@RequestBody ActivityStaffModel model){
        return service.addStaffInActivity(model);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ActivityStaffListItem updateStaffData(@PathVariable(value="id") String id, @RequestBody ActivityStaffModel model){
        return service.updateStaffActivity(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        service.deleteActivityStaff(id);
    }

}
