package com.avegarlabs.hiringservice.services;


import com.avegarlabs.hiringservice.dto.ActivityListItem;
import com.avegarlabs.hiringservice.dto.ActivityStaffListItem;
import com.avegarlabs.hiringservice.dto.ActivityStaffModel;
import com.avegarlabs.hiringservice.dto.WorkerListItem;
import com.avegarlabs.hiringservice.models.Activity;
import com.avegarlabs.hiringservice.models.ActivityStaff;
import com.avegarlabs.hiringservice.repositories.ActivityStaffRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityStaffService {

    private final ActivityService activityService;
    private final HrUtilService hrUtilService;
    private final ActivityStaffRespository respository;

    public List<ActivityStaffListItem> getAll(String activityId){
        return respository.findAllByActivityId(activityId).stream().map(this::mapActivityStaffListItemFromEntity).collect(Collectors.toList());
    }

    public ActivityStaffListItem addStaffInActivity(ActivityStaffModel model){
        ActivityStaff staff = mapActivityStaffToModel(model);
        respository.save(staff);
        return mapActivityStaffListItemFromEntity(staff);
    }

    public ActivityStaffListItem updateStaffActivity(String id, ActivityStaffModel  model){
        ActivityStaff activity = respository.findById(id).get();
        ActivityStaff updateModel = mapUpdateActivityStaffToModel(activity, model);
        respository.save(updateModel);
        return mapActivityStaffListItemFromEntity(updateModel);
    }

    public void deleteActivityStaff(String id){
        respository.deleteById(id);
    }

    public ActivityStaffListItem mapActivityStaffListItemFromEntity(ActivityStaff activityStaff){
        WorkerListItem worker = hrUtilService.findWorkerById(activityStaff.getWorkerId());
        ActivityListItem activityListItem = activityService.findAndMapById(activityStaff.getActivity().getId());
        return  ActivityStaffListItem.builder()
                .id(activityStaff.getId())
                .activityListItem(activityListItem)
                .workerListItem(worker)
                .hours(activityStaff.getHours())
                .value(activityStaff.getValue())
                .build();
    }

    public ActivityStaff mapActivityStaffToModel(ActivityStaffModel model){
       Activity activity = activityService.findActById(model.getActivityId());
        return ActivityStaff.builder()
                .workerId(model.getWorkerId())
                .activity(activity)
                .hours(model.getHours())
                .value(model.getValue())
                .build();
    }

    public ActivityStaff mapUpdateActivityStaffToModel(ActivityStaff entity,  ActivityStaffModel model){
        Activity activity = activityService.findActById(model.getActivityId());
        return ActivityStaff.builder()
                .id(entity.getId())
                .workerId(model.getWorkerId())
                .activity(activity)
                .hours(model.getHours())
                .value(model.getValue())
                .build();
    }
}
