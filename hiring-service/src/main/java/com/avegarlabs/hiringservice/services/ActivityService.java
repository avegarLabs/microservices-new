package com.avegarlabs.hiringservice.services;


import com.avegarlabs.hiringservice.dto.ActivityListItem;
import com.avegarlabs.hiringservice.dto.ActivityModel;
import com.avegarlabs.hiringservice.models.Activity;
import com.avegarlabs.hiringservice.models.WorkOrder;
import com.avegarlabs.hiringservice.repositories.ActivityRespository;
import com.avegarlabs.hiringservice.repositories.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

    private final ActivityRespository respository;
    private final WorkOrderRepository orderRepository;
    private final WorkOrderService workOrderService;

    public List<ActivityListItem> findAll(){
        return respository.findAll().stream().map(this::mapActivityListItemToActivity).collect(Collectors.toList());
    }

    public Activity findActById(String id){
        return respository.findById(id).get();
    }

    public ActivityListItem findAndMapById(String id){
        Activity activity = respository.findById(id).get();
        return mapActivityListItemToActivity(activity);
    }

    public List<ActivityListItem> getAll(String orderId){
        return respository.findAllByOrderId(orderId).stream().map(this::mapActivityListItemToActivity).collect(Collectors.toList());
    }

    public ActivityListItem addActivity(ActivityModel model){
        Activity activity = mapActivityToActivityModel(model);
        respository.save(activity);
        return mapActivityListItemToActivity(activity);
    }

    public ActivityListItem updateActivity(String id, ActivityModel  model){
        Activity activity = respository.findById(id).get();
        WorkOrder w = orderRepository.findById(model.getWorkOrderId()).get();
        activity.setCode(model.getCode());
        activity.setDescription(model.getDescription());
        activity.setContractValue(model.getContractValue());
        activity.setOrder(w);
        activity.setActive(model.getActive());
        respository.save(activity);
        return mapActivityListItemToActivity(activity);
    }

    public void deleteActivity(String id){
        respository.deleteById(id);
    }

    public ActivityListItem mapActivityListItemToActivity(Activity activity){
        WorkOrder order = orderRepository.findById(activity.getOrder().getId()).get();
        return  ActivityListItem.builder()
                .id(activity.getId())
                .code(activity.getCode())
                .description(activity.getDescription())
                .contractValue(activity.getContractValue())
                .actType(activity.getActType())
                .workOrder(workOrderService.mapWorkOrderListItemToWorkOrderModel(order))
                .active(activity.getActive())
                .name(activity.getCode() + " " + activity.getDescription())
                .build();
    }

    public Activity mapActivityToActivityModel(ActivityModel model){
        WorkOrder order = orderRepository.findById(model.getWorkOrderId()).get();
        return Activity.builder()
                .code(model.getCode())
                .description(model.getDescription())
                .contractValue(model.getContractValue())
                .actType(model.getActType())
                .order(order)
                .active(model.getActive())
                .build();
    }
}
