package com.avegarlabs.hiringservice.controllers;

import com.avegarlabs.hiringservice.dto.ActivityListItem;
import com.avegarlabs.hiringservice.dto.ActivityModel;
import com.avegarlabs.hiringservice.services.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring/activity")
@CrossOrigin
public class ActivityController {
    private final ActivityService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<ActivityListItem> allList(){
        return service.findAll();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ActivityListItem getActivity(@PathVariable(value="id") String id){
        return service.findAndMapById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ActivityListItem persistActivity(@RequestBody ActivityModel model){
        return service.addActivity(model);
    }

    @GetMapping("/order/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ActivityListItem> activityList(@PathVariable(value="id") String id){
        return service.getAll(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ActivityListItem updateActivityData(@PathVariable(value="id") String id, @RequestBody ActivityModel model){
        return service.updateActivity(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        service.deleteActivity(id);

    }

}
