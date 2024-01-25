package com.avegarlabs.productionservice.services;

import com.avegarlabs.productionservice.dto.*;
import com.avegarlabs.productionservice.models.Coeficients;
import com.avegarlabs.productionservice.models.Pr3;
import com.avegarlabs.productionservice.repositories.CoeficientsRepository;
import com.avegarlabs.productionservice.repositories.Pr3Repository;
import com.avegarlabs.productionservice.utils.DateData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class Pr3Service {
    private final Pr3Repository repository;
    private final CoeficientsRepository coeficientsRepository;
    private final HrUtilService hrUtilService;
    private final HiringUtilsService hiringUtilsService;
    private final SaleUtilsService saleUtilsService;
    private final DateData dateData;


    public List<Pr3ListItem> getAllData() {
        return repository.findAll().parallelStream().map(this::mapPr3ListItemtoPr3).toList();
    }

    public List<Pr3ListItem> findInCurrentMonth() {
        List<Pr3> list = repository.findAll().parallelStream().filter(item -> item.getCreationDate().trim().equals(dateData.currentMonth())).toList();
        return list.stream().map(this::mapPr3ListItemtoPr3).collect(Collectors.toList());
    }


    public void fixValueData(String id, double value) {
        List<Pr3> list = repository.findAll().parallelStream().filter(item -> item.getCreationDate().trim().equals(dateData.currentMonth()) && item.getActivityId().trim().equals(id)).toList();
        if (list.size() > 0) {
            List<Pr3> updateList = new ArrayList<>();
            for (Pr3 pr3 : list) {
                pr3.setProductionFixe(new BigDecimal(value));
            }
            repository.saveAll(updateList);
        } else {
            persistBeforeReport(id, value);
        }
    }

    private void persistBeforeReport(String id, double value) {
        Pr3 pr3 = Pr3.builder()
                .hours(new BigDecimal("0.0"))
                .extHours(new BigDecimal("0.0"))
                .production(new BigDecimal("0.0"))
                .productionFixe(new BigDecimal(value))
                .creationDate(dateData.currentMonth())
                .lastUpdateTime(dateData.today())
                .activityId(id)
                .workerId(0)
                .build();
        repository.save(pr3);
    }

    public List<FixePr3ListItem> toFixProductionData() {
        List<String> activityIds = repository.findAll().stream().map(Pr3::getActivityId).toList();
        List<String> filterList = new HashSet<>(activityIds).stream().toList();
        List<Pr3> dataList = repository.findAll();
        List<FixePr3ListItem> resultList = new ArrayList<>();
        for (String activityId : filterList) {
            resultList.add(mapFixePr3ListItemToPr3(activityId, dataList));
        }
        return resultList;
    }

    public Pr3StaticsListItem getStatics() {
        return Pr3StaticsListItem.builder()
                .departamentStatics(getProductionByDepertament())
                .servicesTypesStatics(getProductionByServicesTypes())
                .build();
    }

    public List<ServicesTypesStaticsListItem> getProductionByServicesTypes() {
        List<ActTypeListItem> servicesTypes = hrUtilService.getTypesList().getTipos();
        List<Pr3ListItem> dataList = repository.findAll().stream().map(this::mapPr3ListItemtoPr3).toList();
        List<ServicesTypesStaticsListItem> resultList = new ArrayList<>();
        for (ActTypeListItem types : servicesTypes) {
            List<Pr3ListItem> filterList = dataList.parallelStream().filter(item -> item.getActivityListItem().getActType() == types.getId()).toList();
            ServicesTypesStaticsListItem item = calProductionInTypeOfServices(types, filterList);
            if (item != null) {
                resultList.add(item);
            }
        }
        return resultList;
    }


    private ServicesTypesStaticsListItem calProductionInTypeOfServices(ActTypeListItem type, List<Pr3ListItem> dataList) {
        double proVal = 0.0;
        proVal = calcSumProd(dataList);
        return proVal != 0.0 ? new ServicesTypesStaticsListItem(type, new BigDecimal(proVal)) : null;
    }


    public List<DepartamentStaticsListItem> resumeByDepertament() {
        List<Pr3ListItem> dataList = findInCurrentMonth();
        return getDepartamentStaticsListItems(dataList);
    }

    private List<DepartamentStaticsListItem> getDepartamentStaticsListItems(List<Pr3ListItem> dataList) {
        Set<DepartamentListItem> departaments = dataList.stream().map(pr3ListItem -> pr3ListItem.getActivityListItem().getWorkOrder().getProject().getDepartament()).collect(Collectors.toSet());
        List<DepartamentStaticsListItem> resultList = new ArrayList<>();
        for (DepartamentListItem departament : departaments) {
            List<Pr3ListItem> filterList = dataList.parallelStream().filter(item -> item.getActivityListItem().getWorkOrder().getProject().getDepartament().getId() == departament.getId()).toList();
            DepartamentStaticsListItem item = calProductionInDepartament(departament, filterList);
            if (item != null) {
                resultList.add(item);
            }
        }

        return resultList;
    }


    public List<DepartamentStaticsListItem> getProductionByDepertament() {
        List<Pr3ListItem> dataList = repository.findAll().stream().map(this::mapPr3ListItemtoPr3).toList();
        return getDepartamentStaticsListItems(dataList);
    }

    private DepartamentStaticsListItem calProductionInDepartament(DepartamentListItem departament, List<Pr3ListItem> dataList) {
        double proVal = 0.0;
        proVal = calcSumProd(dataList);
        return proVal != 0.0 ? new DepartamentStaticsListItem(departament, new BigDecimal(proVal)) : null;
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


    public Pr3ListItem createPr3(Pr3Model model) {
        Pr3 pr3 = mapPr3ToPr3Model(model);
        repository.save(pr3);
        return mapPr3ListItemtoPr3(pr3);
    }


    public Pr3ListItem updatePr3(String id, Pr3Model model) {
        Pr3 pr3 = repository.findById(id).get();
        Coeficients coeficient = coeficientsRepository.findAll().get(0);
        double calcProductions = coeficient.getProdCoeficient().doubleValue() * model.getHours().doubleValue();
        pr3.setHours(model.getHours());
        pr3.setExtHours(model.getExtHours());
        pr3.setProduction(new BigDecimal(calcProductions));
        pr3.setProductionFixe(model.getProductionFixe());
        pr3.setLastUpdateTime(dateData.today());
        pr3.setActivityId(model.getActivityId());
        pr3.setWorkerId(model.getWorkerId());
        repository.save(pr3);
        return mapPr3ListItemtoPr3(pr3);
    }


    public void deletePr3(String id) {
        repository.deleteById(id);
    }


    public Pr3ListItem mapPr3ListItemtoPr3(Pr3 model) {
        ActivityListItem activityListItem = findActivityByWorker(model.getActivityId());
        WorkerListItem workerListItem = model.getWorkerId() != 0 ? findWorkerById(model.getWorkerId()) : fixWorker();
        return Pr3ListItem.builder()
                .id(model.getId())
                .hours(model.getHours())
                .extHours(model.getExtHours())
                .lastUpdateTime(model.getLastUpdateTime())
                .activityListItem(activityListItem)
                .worker(workerListItem)
                .production(model.getProduction())
                .productionFixe(model.getProductionFixe())
                .creationDate(model.getCreationDate())
                .build();
    }

    private WorkerListItem fixWorker() {
        return WorkerListItem.builder()
                .apellidos("Costos reportados anteriormente")
                .name("Costos reportados anteriormente")
                .ci("1111")
                .codigo_interno("000")
                .salario_escala(new BigDecimal(1))
                .salario_escala_ref(new BigDecimal(1))
                .primer_nombre("Costos reportados anteriormente")
                .segundo_nombre("Costos reportados anteriormente")
                .salario_total_reforma(new BigDecimal(1))
                .build();
    }

    public Pr3 mapPr3ToPr3Model(Pr3Model model) {
        Coeficients coeficient = coeficientsRepository.findAll().get(0);
        double calcProductions = coeficient.getProdCoeficient().doubleValue() * model.getHours().doubleValue();
        return Pr3.builder()
                .hours(model.getHours())
                .extHours(model.getExtHours())
                .production(new BigDecimal(calcProductions))
                .productionFixe(model.getProductionFixe())
                .creationDate(dateData.currentMonth())
                .lastUpdateTime(dateData.today())
                .activityId(model.getActivityId())
                .workerId(model.getWorkerId())
                .build();
    }

    public FixePr3ListItem mapFixePr3ListItemToPr3(String activityId, List<Pr3> pr3List) {
        return FixePr3ListItem.builder()
                .fixValue(new BigDecimal(calcProductionFixInMonth(activityId, pr3List)))
                .activityListItem(findActivityByWorker(activityId))
                .production(new BigDecimal(calcProductionInMonth(activityId, pr3List)))
                .sale(new BigDecimal(getSaleByActivityId(activityId)))
                .sumProd(new BigDecimal(calcSumProd(activityId, pr3List)))
                .SumSale(new BigDecimal(getSumSaleByActivityId(activityId)))
                .build();
    }

    private double calcProductionInMonth(String actId, List<Pr3> pr3List) {
        return pr3List.parallelStream().filter(item -> item.getActivityId().trim().equals(actId) && item.getCreationDate().trim().equals(dateData.currentMonth())).map(i -> i.getProduction().doubleValue()).reduce(0.0, Double::sum);
    }

    private double calcProductionFixInMonth(String actId, List<Pr3> pr3List) {
        List<Pr3> curentList = pr3List.parallelStream()
                .filter(item -> item.getActivityId().trim().equals(actId) && item.getCreationDate().trim().equals(dateData.currentMonth()))
                .toList();

        if (curentList.isEmpty()) {
            return 0.0;
        }

        return curentList.stream()
                .filter(item -> item.getProductionFixe().doubleValue() != 0.00)
                .findFirst()
                .map(Pr3::getProductionFixe)
                .orElse(new BigDecimal("0.0"))
                .doubleValue();
    }

    private double calcSumProd(String actId, List<Pr3> pr3List) {
        double simplePro = pr3List.parallelStream().filter(item -> item.getActivityId().trim().equals(actId) && item.getProductionFixe().doubleValue() == 0.00).map(i -> i.getProduction().doubleValue()).reduce(0.0, Double::sum);
        Set<String> reportMonth = pr3List.parallelStream().filter(item -> item.getActivityId().trim().equals(actId) && item.getProductionFixe().doubleValue() != 0.00).map(Pr3::getCreationDate).collect(Collectors.toSet());
        double sumFix = 0.0;
        for (String s : reportMonth) {
            double val = pr3List.parallelStream().filter(item -> item.getActivityId().trim().equals(actId) && item.getProductionFixe().doubleValue() != 0.00 && item.getCreationDate().trim().equals(s)).map(item -> item.getProductionFixe().doubleValue()).toList().get(0).doubleValue();
            sumFix += val;
        }
        return simplePro + sumFix;
    }

    private WorkerListItem findWorkerById(int workerId) {
        return hrUtilService.getPersonalList().getPersonal().parallelStream().filter(item -> item.getId() == workerId).findFirst().get();
    }

    private ActivityListItem findActivityByWorker(String id) {
        return hiringUtilsService.getActivityList().parallelStream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    }

    private double getSaleByActivityId(String activityId) {
        SaleListItem saleListItem = saleUtilsService.getSalesList()
                .parallelStream()
                .filter(item -> {
                    ActivityListItem activity = item.getActivity();
                    return activity != null && activity.getId().trim().equals(activityId);
                })
                .findFirst()
                .orElse(null);
        return saleListItem != null ? saleListItem.getValue().doubleValue() : 0.0;
    }

    private double getSumSaleByActivityId(String activityId) {
        SaleListItem saleListItem = saleUtilsService.getAllSalesList()
                .parallelStream()
                .filter(item -> {
                    ActivityListItem activity = item.getActivity();
                    return activity != null && activity.getId().trim().equals(activityId);
                })
                .findFirst()
                .orElse(null);

        return saleListItem != null ? saleListItem.getValue().doubleValue() : 0.0;
    }


    public List<ResumeProdActList> prodActListList(){
        List<ResumeProdActList> resumeProdActLists = new ArrayList<>();
        List<Pr3ListItem> pr3ListItems = getAllData();
        Set<ActivityListItem> activityListItemSet = pr3ListItems.parallelStream().map(Pr3ListItem::getActivityListItem).collect(Collectors.toSet());
        for (ActivityListItem activityListItem : activityListItemSet) {
            List<Pr3ListItem> filterByActivityList = pr3ListItems.parallelStream().filter(pr3ListItem -> pr3ListItem.getActivityListItem().getId().equals(activityListItem.getId())).toList();
            double prodVal = calcSumProd(filterByActivityList);
            resumeProdActLists.add(new ResumeProdActList(new BigDecimal(prodVal), activityListItem));
        }
        return resumeProdActLists;
    }





}
