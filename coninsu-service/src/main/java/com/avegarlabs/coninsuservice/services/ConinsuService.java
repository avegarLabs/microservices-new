package com.avegarlabs.coninsuservice.services;

import com.avegarlabs.coninsuservice.dto.*;
import com.avegarlabs.coninsuservice.models.Coninsu;
import com.avegarlabs.coninsuservice.models.Formaters;
import com.avegarlabs.coninsuservice.repositories.ConinsuRepository;
import com.avegarlabs.coninsuservice.repositories.FormaterRepository;
import com.avegarlabs.coninsuservice.utils.DateData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConinsuService {
    private final HiringUtilsService hiringUtilsService;
    private final FormaterRepository formaterRepository;
    private final ConinsuRepository repository;
    private final FormaterService formaterService;
    private final DateData dateData;
    private final HrUtilService hrUtilService;


    public ConinsuStaticsListItem getStatics(){
        return ConinsuStaticsListItem.builder()
                .departamentStatics(getProductionByDepertament())
                .servicesTypesStatics(getProductionByServicesTypes())
                .build();
    }

    public List<ServicesTypesStaticsListItem> getProductionByServicesTypes(){
        List<ActTypeListItem> servicesTypes = hrUtilService.getTypesList().getTipos();
        List<ConinsuListItem> dataList = repository.findAll().stream().map(this::mapConinsuListItemtoConinsu).toList();
        List<ServicesTypesStaticsListItem> resultList = new ArrayList<>();
        for (ActTypeListItem types : servicesTypes) {
            ServicesTypesStaticsListItem item = calProductionInTypeOfServices(types, dataList);
            if(item != null) {
                resultList.add(item);
            }
        }

        return resultList;
    }


    private ServicesTypesStaticsListItem calProductionInTypeOfServices(ActTypeListItem type, List<ConinsuListItem> dataList) {
        double proVal = 0.0;
        proVal = calcSumProdType(type.getId(), dataList);
        return proVal != 0.0 ? new ServicesTypesStaticsListItem(type, new BigDecimal(proVal)) : null;
    }

    private double calcSumProdType(int id, List<ConinsuListItem> pr3List) {
        return pr3List.parallelStream().filter(item -> item.getActivity().getActType() == id).map(i -> i.getCount().doubleValue() * i.getFormater().getPrice().doubleValue()).reduce(0.0, Double::sum);
    }


    public List<DepartamentStaticsListItem> getProductionByDepertament(){
        List<DepartamentListItem> departaments = hrUtilService.getDepartamentList().getDepartamentos();
        List<ConinsuListItem> dataList = repository.findAll().stream().map(this::mapConinsuListItemtoConinsu).toList();
        List<DepartamentStaticsListItem> resultList = new ArrayList<>();
        for (DepartamentListItem departament : departaments) {
            DepartamentStaticsListItem item = calProductionInDepartament(departament, dataList);
            if(item != null) {
                resultList.add(item);
            }
        }

        return resultList;
    }

    private DepartamentStaticsListItem calProductionInDepartament(DepartamentListItem departament, List<ConinsuListItem> dataList) {
        double proVal = 0.0;
        proVal = calcSumProdDept(departament.getId(), dataList);
        return proVal != 0.0 ? new DepartamentStaticsListItem(departament, new BigDecimal(proVal)) : null;
    }

    private double calcSumProdDept(int departamentId, List<ConinsuListItem> pr3List){
        return pr3List.parallelStream().filter(item -> item.getActivity().getWorkOrder().getProject().getDepartament().getId() == departamentId).map(i -> i.getCount().doubleValue() * i.getFormater().getPrice().doubleValue()).reduce(0.0, Double::sum);
    }


    public List<ConinsuListItem> getAll(){
        return repository.findAll().stream().map(this::mapConinsuListItemtoConinsu).toList();
    }

   public List<ConinsuListItem> findInCurrentMonth(){
        List<Coninsu> list = repository.findAll().parallelStream().filter(item -> item.getCreationDate().trim().equals(dateData.currentMonth())).toList();
        return list.stream().map(this::mapConinsuListItemtoConinsu).toList();
   }

    public ConinsuListItem saveConinsu(ConinsuModel model){
            Coninsu coninsu = mapConinsuToConinsuModel(model);
            repository.save(coninsu);
            return mapConinsuListItemtoConinsu(coninsu);

    }

    public ConinsuListItem updateConinsu(String id, ConinsuModel coninsuModel){
        Coninsu coninsu = repository.findById(id).get();
        Formaters f = formaterRepository.findById(coninsuModel.getFormaterId()).get();
        coninsu.setFormater(f);
        coninsu.setActivityId(coninsu.getActivityId());
        coninsu.setCount(coninsuModel.getCount());
        coninsu.setLastUpdateTime(dateData.today());
        repository.save(coninsu);
        return mapConinsuListItemtoConinsu(coninsu);
    }

    public void deleteConinsu(String id){
        repository.deleteById(id);
    }


    public Coninsu mapConinsuToConinsuModel(ConinsuModel model){
        Formaters formaters = formaterRepository.findById(model.getFormaterId()).get();
        return Coninsu.builder()
                .count(model.getCount())
                .activityId(model.getActivityId())
                .formater(formaters)
                .creationDate(dateData.currentMonth())
                .lastUpdateTime(dateData.today())
                .build();
    }


    private ConinsuListItem mapConinsuListItemtoConinsu(Coninsu coninsu){
        ActivityListItem activityListItem = findActivityListItem(coninsu.getActivityId());
        return ConinsuListItem.builder()
                .id(coninsu.getId())
                .count(coninsu.getCount())
                .formater(formaterService.mapFormaterListItemToFormater(coninsu.getFormater()))
                .activity(activityListItem)
                .lastUpdateTime(coninsu.getLastUpdateTime())
                .build();
    }



    private ActivityListItem findActivityListItem(String id){
        return hiringUtilsService.getActivityList().parallelStream().filter(item -> item.getId().equals(id)).findFirst().get();
    }
}
