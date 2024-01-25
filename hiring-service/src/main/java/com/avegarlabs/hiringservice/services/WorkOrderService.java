package com.avegarlabs.hiringservice.services;

import com.avegarlabs.hiringservice.dto.WorkOrderListItem;
import com.avegarlabs.hiringservice.dto.WorkOrderModel;
import com.avegarlabs.hiringservice.models.Project;
import com.avegarlabs.hiringservice.models.WorkOrder;
import com.avegarlabs.hiringservice.repositories.ProjectRepository;
import com.avegarlabs.hiringservice.repositories.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkOrderService {
    private final WorkOrderRepository repository;
    private final ProjectService projectService;
    private final ProjectRepository projectRepository;

    public List<WorkOrderListItem> getAll() {
        return repository.findAll().stream().map(this::mapWorkOrderListItemToWorkOrderModel).collect(Collectors.toList());
    }

    public List<WorkOrderListItem> getAllOrder(String projectId) {
        return repository.findWorkOrdersByProjectId(projectId).stream().map(this::mapWorkOrderListItemToWorkOrderModel).collect(Collectors.toList());
    }

    public WorkOrderListItem addWorkOrder(WorkOrderModel model) {
        WorkOrder order = mapWorkOrderToWorkOrderModel(model);
        repository.save(order);
        return mapWorkOrderListItemToWorkOrderModel(order);
    }


    public WorkOrderListItem updateWorkOrder(String id, WorkOrderModel model) {
        WorkOrder order = repository.findById(id).get();
        order.setCode(model.getCode());
        order.setDescription(model.getDescription());
        repository.save(order);
        return mapWorkOrderListItemToWorkOrderModel(order);

    }

    public void deleteOrder(String id) {
        repository.deleteById(id);
    }


    public WorkOrderListItem mapWorkOrderListItemToWorkOrderModel(WorkOrder model) {
        return WorkOrderListItem.builder()
                .id(model.getId())
                .code(model.getCode())
                .description(model.getDescription())
                .project(projectService.mapProjectListItemToProjectModel(model.getProject()))
                .name(model.getCode() + " " + model.getDescription())
                .build();
    }

    public WorkOrder mapWorkOrderToWorkOrderModel(WorkOrderModel orderModel) {
        Project p = projectRepository.findById(orderModel.getProjectId()).get();
        return WorkOrder.builder()
                .code(orderModel.getCode())
                .description(orderModel.getDescription())
                .project(p)
                .build();
    }
}
