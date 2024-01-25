package com.avegarlabs.hiringservice.controllers;


import com.avegarlabs.hiringservice.dto.ProjectListItem;
import com.avegarlabs.hiringservice.dto.ProjectModel;
import com.avegarlabs.hiringservice.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring/project")
@CrossOrigin
public class ProjectController {
    private final ProjectService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("backend-admin")
    public List<ProjectListItem> findAll(){
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ProjectListItem persistProject(@RequestBody ProjectModel model){
        return service.addProject(model);
    }

    @GetMapping("/by/{moniker}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectListItem projectByMoniker(@PathVariable(value="moniker") String moniker){
        return service.getProjectByMoniker(moniker);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectListItem updateProjectData(@PathVariable(value="id") String id, @RequestBody ProjectModel model){
        return service.updateProject(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable(value="id") String id){
        service.deleteProject(id);

    }

}
