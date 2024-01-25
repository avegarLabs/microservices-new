package com.avegarlabs.hiringservice.services;

import com.avegarlabs.hiringservice.dto.*;
import com.avegarlabs.hiringservice.models.Project;
import com.avegarlabs.hiringservice.models.ServiceRequest;
import com.avegarlabs.hiringservice.models.WorkOrder;
import com.avegarlabs.hiringservice.repositories.ProjectRepository;
import com.avegarlabs.hiringservice.utils.CreateMoniker;
import com.avegarlabs.hiringservice.utils.DateData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository repository;
    private final ServiceRequestService serviceRequestService;
    private final DateData dateData;
    private final CreateMoniker createMoniker;
    private final HrUtilService hrUtilService;


    public List<ProjectListItem> getAll() {
        return repository.findAll().stream().map(this::mapProjectListItemToProjectModel).collect(Collectors.toList());
    }

    public ProjectListItem getProjectByMoniker(String moniker){
        Project project = repository.findProjectByMoniker(moniker);
        return mapProjectListItemToProjectModel(project);
    }

    public ProjectListItem addProject(ProjectModel model) {
        Project project = mapProjectToProjectModel(model);
        repository.save(project);
        return mapProjectListItemToProjectModel(project);
    }


    public ProjectListItem updateProject(String id, ProjectModel model) {
        Project project = repository.findById(id).get();
       Project updateProject = updateProjectToProjectModel(project, model);
        repository.save(updateProject);
        return mapProjectListItemToProjectModel(updateProject);

    }

    public void deleteProject(String id) {
        repository.deleteById(id);
    }


    public ProjectListItem mapProjectListItemToProjectModel(Project model) {
        ServiceRequestListItem requestListItem = serviceRequestService.mapListItemToServiceRequest(model.getServiceRequest());
        DepartamentListItem departamentListItem = hrUtilService.getDepartamentById(requestListItem.getDirectionId());
        return ProjectListItem.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .duration(model.getDuration())
                .durationAdjust(model.getDurationAdjust())
                .durationCurrentScope(model.getDurationCurrentScope())
                .endCurrentScope(model.getEndCurrentScope())
                .endDateAdjust(model.getEndDateAdjust())
                .endDateEstimate(model.getEndDateEstimate())
                .endDateReal(model.getEndDateReal())
                .initialCurrentScope(model.getInitialCurrentScope())
                .serviceRequest(requestListItem)
                .initialDateAdjust(model.getInitialDateAdjust())
                .initialDateEstimate(model.getInitialDateEstimate())
                .initialDateReal(model.getInitialDateReal())
                .membersCount(model.getMembersCount())
                .planesCount(model.getPlanesCount())
                .shortName(model.getShortName())
                .moniker(model.getMoniker())
                .departament(departamentListItem)
                .build();
    }

    public Project mapProjectToProjectModel(ProjectModel model) {
        ServiceRequest requestListItem = serviceRequestService.getRequestById(model.getServiceRequestId());
        String moniker = createMoniker.createMoniker(model.getName());
        return Project.builder()
                .name(model.getName())
                .description(model.getDescription())
                .duration(model.getDuration())
                .durationAdjust(model.getDurationAdjust())
                .durationCurrentScope(model.getDurationCurrentScope())
                .endCurrentScope(dateData.checkDate(model.getEndCurrentScope()))
                .endDateAdjust(dateData.checkDate(model.getEndDateAdjust()))
                .endDateEstimate(dateData.checkDate(model.getEndDateEstimate()))
                .endDateReal(dateData.checkDate(model.getEndDateReal()))
                .initialCurrentScope(dateData.checkDate(model.getInitialCurrentScope()))
                .serviceRequest(requestListItem)
                .initialDateAdjust(dateData.checkDate(model.getInitialDateAdjust()))
                .initialDateEstimate(dateData.checkDate(model.getInitialDateEstimate()))
                .initialDateReal(dateData.checkDate(model.getInitialDateReal()))
                .membersCount(model.getMembersCount())
                .planesCount(model.getPlanesCount())
                .shortName(model.getShortName())
                .creationDate(dateData.currentMonth())
                .lastUpdateTime(dateData.today())
                .moniker(moniker)
                .build();
    }

    public Project updateProjectToProjectModel(Project entity, ProjectModel model) {
        ServiceRequest requestListItem = serviceRequestService.getRequestById(model.getServiceRequestId());
        String moniker = createMoniker.createMoniker(model.getName());
        return Project.builder()
                .id(entity.getId())
                .name(model.getName())
                .description(model.getDescription())
                .duration(model.getDuration())
                .durationAdjust(model.getDurationAdjust())
                .durationCurrentScope(model.getDurationCurrentScope())
                .endCurrentScope(dateData.checkDate(model.getEndCurrentScope()))
                .endDateAdjust(dateData.checkDate(model.getEndDateAdjust()))
                .endDateEstimate(dateData.checkDate(model.getEndDateEstimate()))
                .endDateReal(dateData.checkDate(model.getEndDateReal()))
                .initialCurrentScope(dateData.checkDate(model.getInitialCurrentScope()))
                .serviceRequest(requestListItem)
                .initialDateAdjust(dateData.checkDate(model.getInitialDateAdjust()))
                .initialDateEstimate(dateData.checkDate(model.getInitialDateEstimate()))
                .initialDateReal(dateData.checkDate(model.getInitialDateReal()))
                .membersCount(model.getMembersCount())
                .planesCount(model.getPlanesCount())
                .shortName(model.getShortName())
                .lastUpdateTime(dateData.today())
                .creationDate(entity.getCreationDate())
                .moniker(moniker)
                .build();
        }
    }





