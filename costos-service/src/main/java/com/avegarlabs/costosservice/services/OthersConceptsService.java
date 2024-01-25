package com.avegarlabs.costosservice.services;

import com.avegarlabs.costosservice.dto.ActivityListItem;
import com.avegarlabs.costosservice.dto.OtherConceptsListItem;
import com.avegarlabs.costosservice.dto.OtherConceptsModel;
import com.avegarlabs.costosservice.models.OthersConcepts;
import com.avegarlabs.costosservice.repositories.OthersConceptsRepository;
import com.avegarlabs.costosservice.utils.DateData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OthersConceptsService {

    private final OthersConceptsRepository repository;
    private final HiringUtilsService hiringUtilsService;
    private final DateData dateData;


    public List<OtherConceptsListItem> readOtherOConcepts(){
        List<OthersConcepts> list = repository.findByCreationDate(dateData.currentMonth());
        return list.stream().map(this::mapFromOtherConcpts).toList();
    }

    public OtherConceptsListItem createOtherConcepts(OtherConceptsModel model){
        OthersConcepts entity = mapFromOtherConceptsModel(model);
        repository.save(entity);
        return mapFromOtherConcpts(entity);
    }

    public OtherConceptsListItem updateOtherConcepts(String id, OtherConceptsModel model){
        OthersConcepts concepts = repository.findById(id).get();
        concepts.setFoot(model.getFoot());
        concepts.setFuel(model.getFuel());
        concepts.setHotel(model.getHotel());
        concepts.setC1(model.getC1());
        concepts.setC2(model.getC2());
        concepts.setActivityId(model.getActivityId());
        repository.save(concepts);
        return mapFromOtherConcpts(concepts);
    }


    public void deleteConcepts(String id){
        repository.deleteById(id);
    }



    public OtherConceptsListItem mapFromOtherConcpts(OthersConcepts entity){
        ActivityListItem activityListItem = hiringUtilsService.findById(entity.getActivityId());
        return OtherConceptsListItem.builder()
                .id(entity.getId())
                .foot(entity.getFoot())
                .fuel(entity.getFuel())
                .hotel(entity.getHotel())
                .c1(entity.getC1())
                .c2(entity.getC2())
                .lastUpdateTime(entity.getLastUpdateTime())
                .activity(activityListItem)
                .build();
    }

    public OthersConcepts mapFromOtherConceptsModel(OtherConceptsModel model){
         return OthersConcepts.builder()
                 .fuel(model.getFuel())
                 .foot(model.getFoot())
                 .hotel(model.getHotel())
                 .c1(model.getC1())
                 .c2(model.getC2())
                 .activityId(model.getActivityId())
                 .creationDate(dateData.currentMonth())
                 .lastUpdateTime(dateData.today())
                 .build();
    }
}
