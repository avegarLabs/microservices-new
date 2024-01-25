package com.avegarlabs.hiringservice.services;


import com.avegarlabs.hiringservice.dto.*;
import com.avegarlabs.hiringservice.models.Activity;
import com.avegarlabs.hiringservice.models.ServiceType;
import com.avegarlabs.hiringservice.models.WorkOrder;
import com.avegarlabs.hiringservice.repositories.ActivityRespository;
import com.avegarlabs.hiringservice.repositories.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasicStaticService {

    private final ContratsService contratsService;
    private final HrUtilService hrUtilService;
    private final ActivityService activityService;
    private final Pr3UtilsService pr3UtilsService;
    private final SaleUtilsService saleUtilsService;

    public List<GroupStaticsModel> groupByDepartament() {
        List<GroupStaticsModel> list = new ArrayList<>();
        List<ContractsListItem> contractasList = contratsService.findAll();
        Set<Integer> idsDepartamentsInContracts = contractasList.stream().map(contractsListItem -> contractsListItem.getServiceRequest().getDirectionId()).collect(Collectors.toSet());
        for (Integer ids : idsDepartamentsInContracts) {
            int count = contractasList.parallelStream().filter(item -> item.getServiceRequest().getDirectionId() == ids).toList().size();
            double valOfContracts = getSumOfContractsByDepartaments(ids);
            String dptoName = getDepartamentNameById(ids);
            list.add(new GroupStaticsModel(dptoName, count, new BigDecimal(valOfContracts)));
        }
        return list;
    }

    private double getSumOfContractsByDepartaments(Integer ids) {
        List<ActivityListItem> activityListItems = activityService.findAll();
        List<ActivityListItem> filterActivities = activityListItems.parallelStream().filter(act -> act.getWorkOrder().getProject().getServiceRequest().getDirectionId() == ids).toList();
        return filterActivities.stream().map(activityListItem -> activityListItem.getContractValue().doubleValue()).reduce(0.0, Double::sum);
    }

    private String getDepartamentNameById(int id) {
        return hrUtilService.getDepartamentById(id).getName();
    }

    public List<GroupStaticsModel> groupByServicesType() {
        List<GroupStaticsModel> list = new ArrayList<>();
        List<ContractsListItem> contractasList = contratsService.findAll();
        Set<ServiceTypeListItem> typeListItemSet = contractasList.stream().map(contractsListItem -> contractsListItem.getServiceRequest().getServiceType()).collect(Collectors.toSet());
        for (ServiceTypeListItem typeListItem : typeListItemSet) {
            int count = contractasList.parallelStream().filter(item -> item.getServiceRequest().getServiceType().getId().equals(typeListItem.getId())).toList().size();
            double valOfContracts = getSumOfContractsByServicesTypes(typeListItem.getId());
            list.add(new GroupStaticsModel(typeListItem.getDescription(), count, new BigDecimal(valOfContracts)));

        }
        return list;
    }

    private double getSumOfContractsByServicesTypes(String id) {
        List<ActivityListItem> activityListItems = activityService.findAll();
        List<ActivityListItem> filterActivities = activityListItems.parallelStream().filter(act -> act.getWorkOrder().getProject().getServiceRequest().getServiceType().getId().equals(id)).toList();
        return filterActivities.stream().map(activityListItem -> activityListItem.getContractValue().doubleValue()).reduce(0.0, Double::sum);
    }

    private List<ActivityListItem> activityListItems = new ArrayList<>();
    private List<Pr3ListItem> resumeProdActLists = new ArrayList<>();
    private List<SaleListItem> resumeSaleLists = new ArrayList<>();
    public List<ContractsControlResponse> getControlResponse(){
        activityListItems = activityService.findAll();
        resumeProdActLists = pr3UtilsService.getProductionList();
        resumeSaleLists = saleUtilsService.getAllSalesList();
        List<ContractsControlResponse> list = new ArrayList<>();
        List<ContractsListItem> contractasList = contratsService.findAll();
        for (ContractsListItem contractsListItem : contractasList) {
            ContractsControlResponse response = createContractResponse(contractsListItem);
            list.add(response);
        }
        return  list;
    }

    private ContractsControlResponse createContractResponse(ContractsListItem contractsListItem) {
       double contractValue = getContractValueInActivity(contractsListItem, activityListItems);
       double prod = getProdValueInActivity(contractsListItem, resumeProdActLists);
       double sale = getSalesValueInActivity(contractsListItem, resumeSaleLists);
        double rest = contractValue - sale;
        return ContractsControlResponse.builder()
                .contractNumber(contractsListItem.getNumber())
                .workOrder(contractsListItem.getServiceRequest().getWorkOrder())
                .contractValue(new BigDecimal(contractValue))
                .production(new BigDecimal(prod))
                .sales(new BigDecimal(sale))
                .rest(new BigDecimal(rest))
                .build();
    }

    public double getSalesValueInActivity(ContractsListItem contractsListItem, List<SaleListItem> resumeSaleLists){
        return resumeSaleLists.parallelStream().filter(item -> item.getActivity().getWorkOrder().getProject().getServiceRequest().getId().equals(contractsListItem.getServiceRequest().getId())).map(resumeProdActList -> resumeProdActList.getValue().doubleValue()).reduce(0.0, Double::sum);

    }

    public double getProdValueInActivity(ContractsListItem contractsListItem, List<Pr3ListItem> pr3ListItemList){
        List<Pr3ListItem> filterProductionList = pr3ListItemList.parallelStream().filter(pr3ListItem -> pr3ListItem.getActivityListItem().getWorkOrder().getProject().getServiceRequest().getId().equals(contractsListItem.getServiceRequest().getId())).toList();
        return  calcSumProd(filterProductionList);
    }

    private double calcSumProd(List<Pr3ListItem> pr3List) {
        double simplePro = pr3List.parallelStream().filter(item -> item.getProductionFixe().doubleValue() == 0.00).map(i -> i.getProduction().doubleValue()).reduce(0.0, Double::sum);
        Set<ActivityListItem> actTypeListItems = pr3List.stream().map(Pr3ListItem::getActivityListItem).collect(Collectors.toSet());
        double val = calcSumFixProd(actTypeListItems, pr3List);
        return simplePro + val;
    }

    private double calcSumFixProd(Set<ActivityListItem> actTypeListItems, List<Pr3ListItem> pr3List) {
        double sumFix = 0.0;
        for (ActivityListItem actTypeListItem : actTypeListItems) {
            Set<Double> uniqValue = pr3List.parallelStream().filter(item -> item.getActivityListItem().getId().equals(actTypeListItem.getId())).map(pr3ListItem -> pr3ListItem.getProductionFixe().doubleValue()).collect(Collectors.toSet());
            sumFix += uniqValue.stream().reduce(0.0, Double::sum);
        }
        return sumFix;
    }



    public double getContractValueInActivity(ContractsListItem contractsListItem, List<ActivityListItem> activityListItems){
        List<ActivityListItem> filterActivities = activityListItems.parallelStream().filter(activityListItem ->  activityListItem.getWorkOrder().getProject().getServiceRequest().getId().equals(contractsListItem.getServiceRequest().getId())).toList();
        return  filterActivities.parallelStream().map(activityListItem -> activityListItem.getContractValue().doubleValue()).reduce(0.0, Double::sum);
    }


}




