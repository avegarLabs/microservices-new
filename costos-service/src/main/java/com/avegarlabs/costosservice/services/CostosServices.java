package com.avegarlabs.costosservice.services;

import com.avegarlabs.costosservice.dto.*;
import com.avegarlabs.costosservice.models.*;
import com.avegarlabs.costosservice.repositories.*;
import com.avegarlabs.costosservice.utils.DateData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CostosServices {
    private final Pr3UtilsService pr3UtilsService;
    private final SaleUtilsService saleUtilsService;
    private final VersatUtilsService sqlServerService;
    private final ConinsuUtilsService coninsuUtilsService;
    private final HrUtilService hrUtilService;
    private final DateData dateData;
    private final HiringUtilsService hiringUtilsService;
    private final ActSalaryReportRepository actSalaryReportRepository;
    private final GastIndReportRepository gastIndReportRepository;
    private final GastResulltadoReportRepository gastResulltadoReportRepository;
    private final GastArchivoReportRepository gastArchivoReportRepository;
    private final OthersConceptsRepository othersConceptsRepository;

    private final InProcessCostosRepository inProcessCostosRepository;
    private final MercantilCostosRepository salesCostosRepository;


    public ServicesTypesStaticsItem getStatics() {
        return ServicesTypesStaticsItem.builder()
                .salaryTotal(new BigDecimal(actSalaryReportRepository.findAll().stream().map(actSalaryReport -> actSalaryReport.getTotalSalary().doubleValue()).reduce(0.0, Double::sum)))
                .salaryMonth(new BigDecimal(actSalaryReportRepository.findByCreationDate(dateData.currentMonth()).stream().map(actSalaryReport -> actSalaryReport.getTotalSalary().doubleValue()).reduce(0.0, Double::sum)))
                .archTotal(new BigDecimal(gastArchivoReportRepository.findAll().stream().map(actSalaryReport -> actSalaryReport.getImporte().doubleValue()).reduce(0.0, Double::sum)))
                .archMonth(new BigDecimal(gastArchivoReportRepository.findByCreationDate(dateData.currentMonth()).stream().map(actSalaryReport -> actSalaryReport.getImporte().doubleValue()).reduce(0.0, Double::sum)))
                .indTotal(new BigDecimal(gastIndReportRepository.findAll().stream().map(actSalaryReport -> actSalaryReport.getImporte().doubleValue()).reduce(0.0, Double::sum)))
                .indMonth(new BigDecimal(gastIndReportRepository.findByCreationDate(dateData.currentMonth()).stream().map(actSalaryReport -> actSalaryReport.getImporte().doubleValue()).reduce(0.0, Double::sum)))
                .resultTotal(new BigDecimal(gastResulltadoReportRepository.findAll().stream().map(actSalaryReport -> actSalaryReport.getImporte().doubleValue()).reduce(0.0, Double::sum)))
                .resultMonth(new BigDecimal(gastResulltadoReportRepository.findByCreationDate(dateData.currentMonth()).stream().map(actSalaryReport -> actSalaryReport.getImporte().doubleValue()).reduce(0.0, Double::sum)))
                .build();
    }

    public List<ActSalaryListItem> saveSalaryResume() {
        List<ActSalaryListModel> list = calSalaryResume();
        List<ActSalaryReport> salaryReports = list.stream().map(this::mapFromActSalaryListModel).toList();
        return actSalaryReportRepository.saveAll(salaryReports).stream().map(this::mapFromActSalaryReport).toList();

    }

    public List<GastIndListItem> saveGastIndReport(double value) {
        List<GastIndListModel> list = calGastIndResport(value);
        List<GastIndReport> gastIndReports = list.stream().map(this::mapGastoIndToModel).toList();
        return gastIndReportRepository.saveAll(gastIndReports).stream().map(this::mapGastoIndListItem).toList();

    }

    public List<GastArchivoListItem> saveGastArchivo(double value) {
        List<GastArchivoListModel> list = calGastArchivoRreport(value);
        List<GastArchReport> gastIndReports = list.stream().map(this::mapGastoArchivotToModel).toList();
        return gastArchivoReportRepository.saveAll(gastIndReports).stream().map(this::mapGastoArchivoListItem).toList();

    }


    public List<GastResultadoListItem> saveGastResultado(double value) {
        List<GastResultadoListModel> list = calGastresulResport(value);
        List<GastResultadoReport> gastIndReports = list.stream().map(this::mapGastoResultToModel).toList();
        return gastResulltadoReportRepository.saveAll(gastIndReports).stream().map(this::mapGastoResultListItem).toList();
    }


    public List<ActSalaryListItem> getSalaryResume() {
        List<ActSalaryReport> list = actSalaryReportRepository.findByCreationDate(dateData.currentMonth());
        return list.stream().map(this::mapFromActSalaryReport).toList();
    }

    public List<GastIndListItem> getGastIndResume() {
        List<GastIndReport> list = gastIndReportRepository.findByCreationDate(dateData.currentMonth());
        return list.stream().map(this::mapGastoIndListItem).toList();
    }

    public List<GastResultadoListItem> getGastResultadoResume() {
        List<GastResultadoReport> list = gastResulltadoReportRepository.findByCreationDate(dateData.currentMonth());
        return list.stream().map(this::mapGastoResultListItem).toList();
    }


    public List<GastArchivoListItem> getGastArchivoResume() {
        List<GastArchReport> list = gastArchivoReportRepository.findByCreationDate(dateData.currentMonth());
        return list.stream().map(this::mapGastoArchivoListItem).toList();
    }


    public List<InProcesCostosListItem> getCostosInProcess() {
        List<InProcessCostos> list = inProcessCostosRepository.findAll();
        return list.stream().map(this::mapToInProcesCostos).toList();
    }


    public List<SalesCostosListItem> getMercantilCostos() {
        List<MercantilCostos> list = salesCostosRepository.findAll();
        return list.stream().map(this::mapToInMercantilCostos).toList();
    }


    public List<InProcesCostosListItem> saveCostosInProcess() {
        List<InProcessCostos> list = inProcessCostosRepository.findAll();
        List<InProcessCostos> persistList = new ArrayList<>();
        List<InProcessCostosModel> inProcessCostos = new ArrayList<>();
        List<InProcesCostosListItem> resultItems = new ArrayList<>();
        if (list.size() > 0) {
            list = calInProcessCostos().stream().map(this::mapToInProcesCostosModel).toList();
            resultItems = persistInProcessCostos(list);
        } else {
            inProcessCostos = calInProcessCostos();
            for (InProcessCostosModel inProcessCosto : inProcessCostos) {
                InProcessCostos item = list.parallelStream().filter(ipc -> ipc.getActivityId().equals(inProcessCosto.getActivityId())).findFirst().orElse(null);
                if (item == null) {
                    persistList.add(mapToInProcesCostosModel(inProcessCosto));
                } else {
                    persistList.add(updateValueInProcesCostosModel(item, inProcessCosto));
                }
            }
            resultItems = persistInProcessCostos(persistList);
        }
        return resultItems;
    }

    public List<SalesCostosListItem> saveMercantilCostos() {
        List<MercantilCostos> list = salesCostosRepository.findAll();
        List<MercantilCostos> persistList = new ArrayList<>();
        List<SalesCostosModel> salesCostosModels = new ArrayList<>();
        List<SalesCostosListItem> resultItems = new ArrayList<>();
        if (list.size() > 0) {
            list = calMercantilCostos().stream().map(this::mapToMercantilCostosModel).toList();
            resultItems = persistMerCostosListItems(list);
        } else {
            salesCostosModels = calMercantilCostos();
            for (SalesCostosModel inProcessCosto : salesCostosModels) {
                MercantilCostos item = list.parallelStream().filter(ipc -> ipc.getActivityId().equals(inProcessCosto.getActivityId())).findFirst().orElse(null);
                if (item == null) {
                    persistList.add(mapToMercantilCostosModel(inProcessCosto));
                } else {
                    persistList.add(updateValueInMercantilCostosModel(item, inProcessCosto));
                }
            }
            resultItems = persistMerCostosListItems(persistList);
        }
        return resultItems;
    }

    @Transactional
    @Modifying
    public List<InProcesCostosListItem> persistInProcessCostos(List<InProcessCostos> list) {
        return inProcessCostosRepository.saveAll(list).stream().map(this::mapToInProcesCostos).toList();
    }

    @Transactional
    @Modifying
    public List<SalesCostosListItem> persistMerCostosListItems(List<MercantilCostos> list) {
        return salesCostosRepository.saveAll(list).stream().map(this::mapToInMercantilCostos).toList();
    }


    public List<ActSalaryListModel> calSalaryResume() {
        List<Pr3ListItem> productionList = pr3UtilsService.getProductionList();
        List<ActivityListItem> listInPr3 = productionList.stream().map(Pr3ListItem::getActivityListItem).collect(Collectors.toSet()).stream().toList();
        List<ActSalaryListModel> result = new ArrayList<>();
        for (ActivityListItem activityListItem : listInPr3) {
            List<Pr3ListItem> filterList = productionList.parallelStream().filter(item -> item.getActivityListItem().getId().equals(activityListItem.getId())).toList();
            double sal = createActSal(filterList);
            double otherSal = createActOtherSal(filterList);
            double realSal = sal + otherSal;
            double vacation = calcVacation(realSal);
            double production = getProdFromList(filterList);
            result.add(new ActSalaryListModel(new BigDecimal(sal).setScale(2, RoundingMode.HALF_UP), new BigDecimal(realSal).setScale(2, RoundingMode.HALF_UP), new BigDecimal(otherSal).setScale(2, RoundingMode.HALF_UP), new BigDecimal(vacation).setScale(2, RoundingMode.HALF_UP), new BigDecimal(production).setScale(2, RoundingMode.HALF_UP), dateData.today(), dateData.currentMonth(), activityListItem.getId()));
        }
        return result;
    }


    public List<InProcessCostosModel> calInProcessCostos() {
        List<ActivityListItem> activityListItems = new ArrayList<>();
        List<Pr3ListItem> productionList = pr3UtilsService.getProductionList();
        List<InProcesCostosListItem> costosListItemList = inProcessCostosRepository.findAll().stream().map(this::mapToInProcesCostos).toList();
        List<InProcessCostosModel> result = new ArrayList<>();
        if (costosListItemList.size() > 0) {
            activityListItems.addAll(productionList.stream().map(Pr3ListItem::getActivityListItem).collect(Collectors.toSet()).stream().toList());
            activityListItems.addAll(costosListItemList.stream().map(InProcesCostosListItem::getActivity).collect(Collectors.toSet()).stream().toList());
            List<ActivityListItem> currentList = activityListItems.stream().collect(Collectors.toSet()).stream().toList();
            for (ActivityListItem activityListItem : currentList) {
                InProcessCostosModel model = calInProcessCostos(activityListItem, costosListItemList);
                result.add(model);
            }
        } else {
            activityListItems.addAll(productionList.stream().map(Pr3ListItem::getActivityListItem).collect(Collectors.toSet()).stream().toList());
            List<ActivityListItem> currentList = activityListItems.stream().collect(Collectors.toSet()).stream().toList();
            for (ActivityListItem activityListItem : currentList) {
                InProcessCostosModel model = firstcalInProcessCostos(activityListItem);
                result.add(model);
            }
        }
        return result;
    }

    public List<SalesCostosModel> calMercantilCostos() {
        List<ActivityListItem> activityListItems = new ArrayList<>();
        List<SaleListItem> productionList = saleUtilsService.getSalesList();
        List<SalesCostosListItem> salesCostosListItemList = salesCostosRepository.findAll().stream().map(this::mapToInMercantilCostos).toList();
        List<SalesCostosModel> result = new ArrayList<>();
        if (salesCostosListItemList.size() > 0) {
            activityListItems.addAll(productionList.stream().map(SaleListItem::getActivity).collect(Collectors.toSet()).stream().toList());
            activityListItems.addAll(salesCostosListItemList.stream().map(SalesCostosListItem::getActivity).collect(Collectors.toSet()).stream().toList());
            List<ActivityListItem> currentList = activityListItems.stream().collect(Collectors.toSet()).stream().toList();
            for (ActivityListItem activityListItem : currentList) {
                SalesCostosModel model = calSalesCostosModel(activityListItem, salesCostosListItemList);
                result.add(model);
            }
        } else {
            activityListItems.addAll(productionList.stream().map(SaleListItem::getActivity).collect(Collectors.toSet()).stream().toList());
            List<ActivityListItem> currentList = activityListItems.stream().collect(Collectors.toSet()).stream().toList();
            for (ActivityListItem activityListItem : currentList) {
                SalesCostosModel model = firstcalMercantilCostos(activityListItem);
                result.add(model);
            }
        }
        return result;
    }


    public InProcessCostosModel calInProcessCostos(ActivityListItem activityListItem, List<InProcesCostosListItem> dataList) {
        List<Pr3ListItem> productionList = pr3UtilsService.getProductionList();
        InProcessCostosModel result = new InProcessCostosModel();
        //this block calculate the historical data
        InProcesCostosListItem procesCostosItem = dataList.parallelStream().filter(item -> item.getActivity().equals(activityListItem.getId())).findFirst().orElse(null);
        double initHours = procesCostosItem != null ? procesCostosItem.getInitHours().doubleValue() + procesCostosItem.getMonthHours().doubleValue() : 0.0;
        double initSalary = procesCostosItem != null ? procesCostosItem.getInitScaleSalary().doubleValue() + procesCostosItem.getMonthScaleSalary().doubleValue() : 0.0;
        double initVacation = procesCostosItem != null ? procesCostosItem.getInitVacation().doubleValue() + procesCostosItem.getMonthVacation().doubleValue() : 0.0;
        double initFot = procesCostosItem != null ? procesCostosItem.getInitFot().doubleValue() + procesCostosItem.getMonthFot().doubleValue() : 0.0;
        double initFuel = procesCostosItem != null ? procesCostosItem.getInitFuel().doubleValue() + procesCostosItem.getMonthFuel().doubleValue() : 0.0;
        double initHotel = procesCostosItem != null ? procesCostosItem.getInitHotel().doubleValue() + procesCostosItem.getMonthHotel().doubleValue() : 0.0;
        double initC1 = procesCostosItem != null ? procesCostosItem.getInitC1().doubleValue() + procesCostosItem.getMonthC1().doubleValue() : 0.0;
        double initC2 = procesCostosItem != null ? procesCostosItem.getInitC2().doubleValue() + procesCostosItem.getMonthC2().doubleValue() : 0.0;
        double initValArch = procesCostosItem != null ? procesCostosItem.getInitGstoArch().doubleValue() + procesCostosItem.getMonthGstoArch().doubleValue() : 0.0;
        double initValInd = procesCostosItem != null ? procesCostosItem.getInitGstoInd().doubleValue() + procesCostosItem.getMonthGstoInd().doubleValue() : 0.0;
        double initCostTotal = procesCostosItem != null ? procesCostosItem.getInitTotalCost().doubleValue() + procesCostosItem.getMonthTotalCost().doubleValue() : 0.0;
        double initProduction = procesCostosItem != null ? procesCostosItem.getInitProdBrut().doubleValue() + procesCostosItem.getMonthProdBrut().doubleValue() : 0.0;
        double initCostoByPesos = initCostTotal / initProduction;

        //this block calculate data in the current month
        GastResultadoReport model = gastResulltadoReportRepository.findByActivityIdAndCreationDate(activityListItem.getId(), dateData.currentMonth());
        double resultadoReport = model != null ? model.getImporte().doubleValue() : 0.0;
        List<Pr3ListItem> filterList = productionList.parallelStream().filter(item -> item.getActivityListItem().getId().equals(activityListItem.getId())).toList();
        double hours = filterList.size() > 0 ? calcHours(filterList) : 0.0;
        double salary = filterList.size() > 0 ? calcSalaryInMonth(activityListItem.getId()) : 0.0;
        double vacation = filterList.size() > 0 ? calcVacationInMonth(activityListItem.getId()) : 0.0;
        OthersConcepts concepts = getConceptsInMonth(activityListItem.getId());
        double fot = concepts != null ? concepts.getFoot().doubleValue() : 0.0;
        double fuel = concepts != null ? concepts.getFuel().doubleValue() : 0.0;
        double hotel = concepts != null ? concepts.getHotel().doubleValue() : 0.0;
        double c1 = concepts != null ? concepts.getC1().doubleValue() : 0.0;
        double c2 = concepts != null ? concepts.getC2().doubleValue() : 0.0;
        double valArch = filterList.size() > 0 ? calcGstoArchInMonth(activityListItem.getId()) : 0.0;
        double valInd = filterList.size() > 0 ? calcGstoIndInMonth(activityListItem.getId()) : 0.0;
        double costTotal = salary + vacation + fot + fuel + hotel + c1 +  c2 +  valArch + valInd;
        double production = getProdFromList(filterList);
        double costoByPesos = costTotal / production;
        result = new InProcessCostosModel(new BigDecimal(initHours).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initSalary).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initVacation).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initFot).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initFuel).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initHotel).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initC1).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initC2).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initValArch).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initValInd).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initCostTotal).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initProduction).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initCostoByPesos).setScale(2, RoundingMode.HALF_UP), new BigDecimal(hours).setScale(2, RoundingMode.HALF_UP), new BigDecimal(salary).setScale(2, RoundingMode.HALF_UP), new BigDecimal(vacation).setScale(2, RoundingMode.HALF_UP), new BigDecimal(fot).setScale(2, RoundingMode.HALF_UP), new BigDecimal(fuel).setScale(2, RoundingMode.HALF_UP), new BigDecimal(hotel).setScale(2, RoundingMode.HALF_UP), new BigDecimal(c1).setScale(2, RoundingMode.HALF_UP), new BigDecimal(c2).setScale(2, RoundingMode.HALF_UP), new BigDecimal(valArch).setScale(2, RoundingMode.HALF_UP), new BigDecimal(valInd).setScale(2, RoundingMode.HALF_UP), new BigDecimal(costTotal).setScale(2, RoundingMode.HALF_UP), new BigDecimal(production).setScale(2, RoundingMode.HALF_UP), new BigDecimal(costoByPesos).setScale(2, RoundingMode.HALF_UP), activityListItem.getId(), new BigDecimal(resultadoReport));
        return result;
    }


    public SalesCostosModel calSalesCostosModel(ActivityListItem activityListItem, List<SalesCostosListItem> dataList) {
        List<Pr3ListItem> productionList = pr3UtilsService.getProductionList();
        List<SaleListItem> salesList = saleUtilsService.getSalesList();
        SalesCostosModel result = new SalesCostosModel();
        //this block calculate the historical data
        SalesCostosListItem procesCostosItem = dataList.parallelStream().filter(item -> item.getActivity().equals(activityListItem.getId())).findFirst().orElse(null);
        double initHours = procesCostosItem != null ? procesCostosItem.getInitHours().doubleValue() + procesCostosItem.getMonthHours().doubleValue() : 0.0;
        double initSalary = procesCostosItem != null ? procesCostosItem.getInitScaleSalary().doubleValue() + procesCostosItem.getMonthScaleSalary().doubleValue() : 0.0;
        double initVacation = procesCostosItem != null ? procesCostosItem.getInitVacation().doubleValue() + procesCostosItem.getMonthVacation().doubleValue() : 0.0;
        double initFot = procesCostosItem != null ? procesCostosItem.getInitFot().doubleValue() + procesCostosItem.getMonthFot().doubleValue() : 0.0;
        double initFuel = procesCostosItem != null ? procesCostosItem.getInitFuel().doubleValue() + procesCostosItem.getMonthFuel().doubleValue() : 0.0;
        double initHotel = procesCostosItem != null ? procesCostosItem.getInitHotel().doubleValue() + procesCostosItem.getMonthHotel().doubleValue() : 0.0;
        double initC1 = procesCostosItem != null ? procesCostosItem.getInitC1().doubleValue() + procesCostosItem.getMonthC1().doubleValue() : 0.0;
        double initC2 = procesCostosItem != null ? procesCostosItem.getInitC2().doubleValue() + procesCostosItem.getMonthC2().doubleValue() : 0.0;
        double initValArch = procesCostosItem != null ? procesCostosItem.getInitGstoArch().doubleValue() + procesCostosItem.getMonthGstoArch().doubleValue() : 0.0;
        double initValInd = procesCostosItem != null ? procesCostosItem.getInitGstoInd().doubleValue() + procesCostosItem.getMonthGstoInd().doubleValue() : 0.0;
        double initCostTotal = procesCostosItem != null ? procesCostosItem.getInitTotalCost().doubleValue() + procesCostosItem.getMonthTotalCost().doubleValue() : 0.0;
        double initProduction = procesCostosItem != null ? procesCostosItem.getInitProdMerc().doubleValue() + procesCostosItem.getMonthProdMerc().doubleValue() : 0.0;
        double initCostoByPesos = initCostTotal / initProduction;

        //this block calculate data in the current month
        List<Pr3ListItem> filterList = productionList.parallelStream().filter(item -> item.getActivityListItem().getId().equals(activityListItem.getId())).toList();
        GastResultadoReport model = gastResulltadoReportRepository.findByActivityIdAndCreationDate(activityListItem.getId(), dateData.currentMonth());
        double resultadoReport = model != null ? model.getImporte().doubleValue() : 0.0;

        double hours = filterList.size() > 0 ? calcHours(filterList) : 0.0;
        double salary = filterList.size() > 0 ? calcSalaryInMonth(activityListItem.getId()) : 0.0;
        double vacation = filterList.size() > 0 ? calcVacationInMonth(activityListItem.getId()) : 0.0;
        OthersConcepts concepts = getConceptsInMonth(activityListItem.getId());
        double fot = concepts != null ? concepts.getFoot().doubleValue() : 0.0;
        double fuel = concepts != null ? concepts.getFuel().doubleValue() : 0.0;
        double hotel = concepts != null ? concepts.getHotel().doubleValue() : 0.0;
        double c1 = concepts != null ? concepts.getC1().doubleValue() : 0.0;
        double c2 = concepts != null ? concepts.getC2().doubleValue() : 0.0;
        double valArch = filterList.size() > 0 ? calcGstoArchInMonth(activityListItem.getId()) : 0.0;
        double valInd = filterList.size() > 0 ? calcGstoIndInMonth(activityListItem.getId()) : 0.0;
        double costTotal = salary + vacation + fot + fuel + hotel + c1 +  c2 +  valArch + valInd;
        double production = salesList.parallelStream().filter(item -> item.getActivity().getId().equals(activityListItem.getId())).map(SaleListItem::getValue).findFirst().get().doubleValue();
        double costoByPesos = costTotal / production;
        result = new SalesCostosModel(new BigDecimal(initHours).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initSalary).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initVacation).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initFot).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initFuel).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initHotel).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initC1).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initC2).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initValArch).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initValInd).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initCostTotal).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initProduction).setScale(2, RoundingMode.HALF_UP), new BigDecimal(initCostoByPesos).setScale(2, RoundingMode.HALF_UP), new BigDecimal(hours).setScale(2, RoundingMode.HALF_UP), new BigDecimal(salary).setScale(2, RoundingMode.HALF_UP), new BigDecimal(vacation).setScale(2, RoundingMode.HALF_UP), new BigDecimal(fot).setScale(2, RoundingMode.HALF_UP), new BigDecimal(fuel).setScale(2, RoundingMode.HALF_UP), new BigDecimal(hotel).setScale(2, RoundingMode.HALF_UP), new BigDecimal(c1).setScale(2, RoundingMode.HALF_UP), new BigDecimal(c2).setScale(2, RoundingMode.HALF_UP), new BigDecimal(valArch).setScale(2, RoundingMode.HALF_UP), new BigDecimal(valInd).setScale(2, RoundingMode.HALF_UP), new BigDecimal(costTotal).setScale(2, RoundingMode.HALF_UP), new BigDecimal(production).setScale(2, RoundingMode.HALF_UP), new BigDecimal(costoByPesos).setScale(2, RoundingMode.HALF_UP), activityListItem.getId(), new BigDecimal(resultadoReport));
        return result;
    }




    public InProcessCostosModel firstcalInProcessCostos(ActivityListItem activityListItem) {
        List<Pr3ListItem> productionList = pr3UtilsService.getProductionList();
        InProcessCostosModel result = new InProcessCostosModel();
        List<Pr3ListItem> filterList = productionList.parallelStream().filter(item -> item.getActivityListItem().getId().equals(activityListItem.getId())).toList();
        GastResultadoReport model = gastResulltadoReportRepository.findByActivityIdAndCreationDate(activityListItem.getId(), dateData.currentMonth());
        double resultadoReport = model != null ? model.getImporte().doubleValue() : 0.0;
        double hours = calcHours(filterList);
        double salary = calcSalaryInMonth(activityListItem.getId());
        double vacation = calcVacationInMonth(activityListItem.getId());
        OthersConcepts concepts = getConceptsInMonth(activityListItem.getId());
        double fot = concepts != null ? concepts.getFoot().doubleValue() : 0.0;
        double fuel = concepts != null ? concepts.getFuel().doubleValue() : 0.0;
        double hotel = concepts != null ? concepts.getHotel().doubleValue() : 0.0;
        double c1 = concepts != null ? concepts.getC1().doubleValue() : 0.0;
        double c2 = concepts != null ? concepts.getC2().doubleValue() : 0.0;
        double valArch = calcGstoArchInMonth(activityListItem.getId());
        double valInd = calcGstoIndInMonth(activityListItem.getId());
        double costTotal = salary + vacation + fot + fuel + hotel + c1 +  c2 +  valArch + valInd;
        double production = getProdFromList(filterList);
        double costoByPesos = costTotal / production;
        result = new InProcessCostosModel(new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(hours).setScale(2, RoundingMode.HALF_UP), new BigDecimal(salary).setScale(2, RoundingMode.HALF_UP), new BigDecimal(vacation).setScale(2, RoundingMode.HALF_UP), new BigDecimal(fot).setScale(2, RoundingMode.HALF_UP), new BigDecimal(fuel).setScale(2, RoundingMode.HALF_UP), new BigDecimal(hotel).setScale(2, RoundingMode.HALF_UP), new BigDecimal(c1).setScale(2, RoundingMode.HALF_UP), new BigDecimal(c2).setScale(2, RoundingMode.HALF_UP),  new BigDecimal(valArch).setScale(2, RoundingMode.HALF_UP), new BigDecimal(valInd).setScale(2, RoundingMode.HALF_UP), new BigDecimal(costTotal).setScale(2, RoundingMode.HALF_UP), new BigDecimal(production).setScale(2, RoundingMode.HALF_UP), new BigDecimal(costoByPesos).setScale(2, RoundingMode.HALF_UP), activityListItem.getId(), new BigDecimal(resultadoReport));
        return result;
    }

    public SalesCostosModel firstcalMercantilCostos(ActivityListItem activityListItem) {
        List<SaleListItem> salesList = saleUtilsService.getSalesList();
        List<Pr3ListItem> productionList = pr3UtilsService.getProductionList();
        SalesCostosModel result = new SalesCostosModel();
        List<Pr3ListItem> filterList = productionList.parallelStream().filter(item -> item.getActivityListItem().getId().equals(activityListItem.getId())).toList();
        GastResultadoReport model = gastResulltadoReportRepository.findByActivityIdAndCreationDate(activityListItem.getId(), dateData.currentMonth());
        double resultadoReport = model != null ? model.getImporte().doubleValue() : 0.0;
        double hours = calcHours(filterList);
        double salary = calcSalaryInMonth(activityListItem.getId());
        double vacation = calcVacationInMonth(activityListItem.getId());
        OthersConcepts concepts = getConceptsInMonth(activityListItem.getId());
        double fot = concepts != null ? concepts.getFoot().doubleValue() : 0.0;
        double fuel = concepts != null ? concepts.getFuel().doubleValue() : 0.0;
        double hotel = concepts != null ? concepts.getHotel().doubleValue() : 0.0;
        double c1 = concepts != null ? concepts.getC1().doubleValue() : 0.0;
        double c2 = concepts != null ? concepts.getC2().doubleValue() : 0.0;
        double valArch = calcGstoArchInMonth(activityListItem.getId());
        double valInd = calcGstoIndInMonth(activityListItem.getId());
        double costTotal = salary + vacation + fot + fuel + hotel + c1 +  c2 +  valArch + valInd;
        double production = salesList.parallelStream().filter(item -> item.getActivity().getId().equals(activityListItem.getId())).map(SaleListItem::getValue).findFirst().get().doubleValue();
        double costoByPesos = costTotal / production;
        result = new SalesCostosModel(new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(0.0), new BigDecimal(hours).setScale(2, RoundingMode.HALF_UP), new BigDecimal(salary).setScale(2, RoundingMode.HALF_UP), new BigDecimal(vacation).setScale(2, RoundingMode.HALF_UP), new BigDecimal(fot).setScale(2, RoundingMode.HALF_UP), new BigDecimal(fuel).setScale(2, RoundingMode.HALF_UP), new BigDecimal(hotel).setScale(2, RoundingMode.HALF_UP), new BigDecimal(c1).setScale(2, RoundingMode.HALF_UP), new BigDecimal(c2).setScale(2, RoundingMode.HALF_UP),  new BigDecimal(valArch).setScale(2, RoundingMode.HALF_UP), new BigDecimal(valInd).setScale(2, RoundingMode.HALF_UP), new BigDecimal(costTotal).setScale(2, RoundingMode.HALF_UP), new BigDecimal(production).setScale(2, RoundingMode.HALF_UP), new BigDecimal(costoByPesos).setScale(2, RoundingMode.HALF_UP), activityListItem.getId(), new BigDecimal(resultadoReport));
        return result;
    }

    private OthersConcepts getConceptsInMonth(String id) {
        return othersConceptsRepository.findByActivityId(id).parallelStream().filter(item -> item.getCreationDate().equals(dateData.currentMonth())).findFirst().orElse(null);
    }

    private double calcVacationInMonth(String id) {
        ActSalaryReport dataInMonth = actSalaryReportRepository.getActSalaryReportsByActivityId(id).parallelStream().filter(item -> item.getCreationDate().equals(dateData.currentMonth())).findFirst().orElse(null);
        return  dataInMonth == null ? 0.0 : dataInMonth.getVacation().doubleValue();
    }

    private double calcSalaryInMonth(String id) {
        ActSalaryReport dataInMonth = actSalaryReportRepository.getActSalaryReportsByActivityId(id).parallelStream().filter(item -> item.getCreationDate().equals(dateData.currentMonth())).findFirst().orElse(null);
        return  dataInMonth == null ? 0.0 :  dataInMonth.getScaleSalary().doubleValue();
    }

    private double calcGstoArchInMonth(String id) {
        GastArchReport gastArchReport = gastArchivoReportRepository.findByActivityId(id).parallelStream().filter(item -> item.getCreationDate().equals(dateData.currentMonth())).findFirst().orElse(null);
        return gastArchReport != null ? gastArchReport.getImporte().doubleValue() : 0.0;
    }

    private double calcGstoIndInMonth(String id) {
        GastIndReport gastIndReport = gastIndReportRepository.findByActivityId(id).parallelStream().filter(item -> item.getCreationDate().equals(dateData.currentMonth())).findFirst().orElse(null);
        return gastIndReport != null ? gastIndReport.getImporte().doubleValue() : 0.0;
    }

    public List<GastIndListModel> calGastIndResport(double valueGsto) {
        List<Pr3ListItem> productionList = pr3UtilsService.getProductionList();
        double totalSal = valueGsto;
        List<ActivityListItem> listInPr3 = productionList.stream().map(Pr3ListItem::getActivityListItem).collect(Collectors.toSet()).stream().toList();
        List<GastIndListModel> result = new ArrayList<>();
        for (ActivityListItem activityListItem : listInPr3) {
           // List<Pr3ListItem> filterList = productionList.parallelStream().filter(item -> item.getActivityListItem().getId().equals(activityListItem.getId())).toList();
            double sal = getSalaryInActivity(activityListItem.getId());
            double coef = sal / totalSal;
            double value = sal * coef;
            result.add(new GastIndListModel(new BigDecimal(sal).setScale(2, RoundingMode.HALF_UP), coef, new BigDecimal(value).setScale(2, RoundingMode.HALF_UP), dateData.currentMonth(), dateData.today(), activityListItem.getId()));
        }
        return result;
    }


    public List<GastResultadoListModel> calGastresulResport(double valueGsto) {
        List<SaleListItem> saleListItems = saleUtilsService.getSalesList();
       // List<Pr3ListItem> productionList = pr3UtilsService.getProductionList();
        double totalSal = valueGsto;
        List<GastResultadoListModel> result = new ArrayList<>();
        for (SaleListItem sales : saleListItems) {
           // List<Pr3ListItem> filterList = productionList.parallelStream().filter(item -> item.getActivityListItem().getId().equals(sales.getActivity().getId())).toList();
            double sal = sales.getValue().doubleValue();
            double coef = sal / totalSal;
            double value = sales.getValue().doubleValue() * coef;
            result.add(new GastResultadoListModel(new BigDecimal(sales.getValue().doubleValue()).setScale(2, RoundingMode.HALF_UP), coef, new BigDecimal(value).setScale(2, RoundingMode.HALF_UP), dateData.currentMonth(), dateData.today(), sales.getActivity().getId()));
        }
        return result;
    }

    public List<GastArchivoListModel> calGastArchivoRreport(double valueGsto) {
        List<ConinsuListItem> coninsuListItemList = coninsuUtilsService.getConinsuList();
        double totalGsto = valueGsto;
        List<ActivityListItem> listInConinsu = coninsuListItemList.stream().map(ConinsuListItem::getActivity).collect(Collectors.toSet()).stream().toList();
        List<GastArchivoListModel> result = new ArrayList<>();
        for (ActivityListItem item : listInConinsu) {
            double val = calGstoInAct(item, coninsuListItemList);
            log.info(item.getName(), val);
            double coef = val / totalGsto;
            double value = totalGsto * coef;
            result.add(new GastArchivoListModel(new BigDecimal(totalGsto).setScale(2, RoundingMode.HALF_UP), coef, new BigDecimal(value).setScale(2, RoundingMode.HALF_UP), dateData.currentMonth(), dateData.today(), item.getId()));
        }
        return result;
    }

    private double calGstoInAct(ActivityListItem item, List<ConinsuListItem> coninsuListItemList) {
        return coninsuListItemList.parallelStream().filter(cn -> cn.getActivity().getId().equals(item.getId())).map(coninsuListItem -> coninsuListItem.getCount().doubleValue() * coninsuListItem.getFormater().getPrice().doubleValue()).reduce(0.0, Double::sum);
    }

    private double calcTotalSal(List<Pr3ListItem> list) {
        return createActSal(list);
    }

    private double calTotalSale() {
        return saleUtilsService.getSalesList().stream().map(item -> item.getValue().doubleValue()).reduce(0.0, Double::sum);
    }

    private double calTotalGastoArchivo(List<ConinsuListItem> list) {
        return list.parallelStream().map(coninsuListItem -> coninsuListItem.getCount().doubleValue() * coninsuListItem.getFormater().getPrice().doubleValue()).reduce(0.0, Double::sum);
    }


    /**
     * private double getProdFromList(List<Pr3ListItem> filterList) {
     * double totalProd = 0.0;
     * double fix = filterList.stream().map(Pr3ListItem::getProductionFixe).toList().get(0).doubleValue();
     * if (fix != 0.00) {
     * totalProd = fix;
     * } else {
     * totalProd = filterList.stream().map(item -> item.getProduction().doubleValue()).reduce(0.0, Double::sum);
     * }
     * return totalProd;
     * }
     */


    private double getProdFromList(List<Pr3ListItem> pr3List) {
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

    private double calcVacation(double sal) {
        return sal * 0.0909;

    }

    private double createActSal(List<Pr3ListItem> filterList) {
        double salBase = 0.0;
        for (Pr3ListItem pr3ListItem : filterList) {
            salBase += salInPr3(pr3ListItem);
        }

        return salBase;
    }

    private double createActOtherSal(List<Pr3ListItem> filterList) {
        double othSal = 0.0;
        for (Pr3ListItem pr3ListItem : filterList) {
            othSal += salMasterInPr3(pr3ListItem);
        }

        return othSal;
    }

    private double getSalaryInActivity(String activityId) {
        ActSalaryReport salaryReport = actSalaryReportRepository.findByActivityIdAndCreationDate(activityId, dateData.currentMonth());
        return salaryReport.getTotalSalary().doubleValue();
    }

    private double salInPr3(Pr3ListItem pr3ListItem) {
        double tar = getDatNonFromPr3(pr3ListItem) / 190.6;
        if (tar == 0.0) {
            log.info("Trabajadores Con problemas CI: " + pr3ListItem.getWorker().getName() + " - " + pr3ListItem.getWorker().getCi());
        }
        return pr3ListItem.getHours().doubleValue() * tar;
    }

    private double salMasterInPr3(Pr3ListItem pr3ListItem) {
        double tar = getDatMaster(pr3ListItem) / 190.6;
        return pr3ListItem.getHours().doubleValue() * tar;
    }

    private double calcHours(List<Pr3ListItem> filterList) {
        return filterList.parallelStream().map(item -> item.getHours().doubleValue()).reduce(0.0, Double::sum);
    }

    public double getDatNonFromPr3(Pr3ListItem item) {
        return hrUtilService.getPersonalList().getPersonal().parallelStream().filter(pw -> pw.getCi().equals(item.getWorker().getCi())).map(workerListItem -> workerListItem.getSalario_escala_ref().doubleValue()).findFirst().orElse(0.0);
    }

    public double getDatMaster(Pr3ListItem item) {
        return hrUtilService.getPersonalList().getPersonal().parallelStream().filter(pw -> pw.getCi().equals(item.getWorker().getCi())).map(workerListItem -> workerListItem.getMaestria().doubleValue()).findFirst().orElse(0.0);
    }

    public InProcesCostosListItem mapToInProcesCostos(InProcessCostos item) {
        ActivityListItem activityListItem = findActivityById(item.getActivityId());
        return InProcesCostosListItem.builder()
                .id(item.getId())
                .initHours(item.getInitHours())
                .initScaleSalary(item.getInitScaleSalary())
                .initVacation(item.getInitVacation())
                .initFot(item.getInitFot())
                .initFuel(item.getInitFuel())
                .initHotel(item.getInitHotel())
                .initC1(item.getInitC1())
                .initC2(item.getInitC2())
                .initGstoArch(item.getInitGstoArch())
                .initGstoInd(item.getInitGstoInd())
                .initTotalCost(item.getInitTotalCost())
                .initProdBrut(item.getInitProdBrut())
                .initCostByPesos(item.getInitCostByPesos())
                .monthHours(item.getMonthHours())
                .monthScaleSalary(item.getMonthScaleSalary())
                .monthVacation(item.getMonthVacation())
                .monthFot(item.getMonthFot())
                .monthFuel(item.getMonthFuel())
                .monthHotel(item.getMonthHotel())
                .monthC1(item.getMonthC1())
                .monthC2(item.getMonthC2())
                .monthGstoArch(item.getMonthGstoArch())
                .monthGstoInd(item.getMonthGstoInd())
                .monthTotalCost(item.getMonthTotalCost())
                .monthProdBrut(item.getMonthProdBrut())
                .monthCostByPesos(item.getMonthCostByPesos())
                .lastUpdateTime(item.getLastUpdateTime())
                .activity(activityListItem)
                .monthGstoResult(item.getMonthGstoResult())
                .build();
    }

    public InProcessCostos mapToInProcesCostosModel(InProcessCostosModel item) {
        return InProcessCostos.builder()
                .initHours(item.getInitHours())
                .initScaleSalary(item.getInitScaleSalary())
                .initVacation(item.getInitVacation())
                .initFot(item.getInitFot())
                .initFuel(item.getInitFuel())
                .initHotel(item.getInitHotel())
                .initC1(item.getInitC1())
                .initC2(item.getInitC2())
                .initGstoArch(item.getInitGstoArch())
                .initGstoInd(item.getInitGstoInd())
                .initTotalCost(item.getInitTotalCost())
                .initProdBrut(item.getInitProdBrut())
                .initCostByPesos(item.getInitCostByPesos())
                .monthHours(item.getMonthHours())
                .monthScaleSalary(item.getMonthScaleSalary())
                .monthVacation(item.getMonthVacation())
                .monthFot(item.getMonthFot())
                .monthFuel(item.getMonthFuel())
                .monthHotel(item.getMonthHotel())
                .monthC1(item.getMonthC1())
                .monthC2(item.getMonthC2())
                .monthGstoArch(item.getMonthGstoArch())
                .monthGstoInd(item.getMonthGstoInd())
                .monthTotalCost(item.getMonthTotalCost())
                .monthProdBrut(item.getMonthProdBrut())
                .monthCostByPesos(item.getMonthCostByPesos())
                .lastUpdateTime(dateData.today())
                .creationDate(dateData.currentMonth())
                .activityId(item.getActivityId())
                .monthGstoResult(item.getMonthGstoResult())
                .build();
    }

    public InProcessCostos updateValueInProcesCostosModel(InProcessCostos entitity, InProcessCostosModel item) {
        entitity.setInitHours(item.getInitHours());
        entitity.setInitScaleSalary(item.getInitScaleSalary());
        entitity.setInitVacation(item.getInitVacation());
        entitity.setInitFot(item.getInitFot());
        entitity.setInitFuel(item.getInitFuel());
        entitity.setInitHotel(item.getInitHotel());
        entitity.setInitC1(item.getInitC1());
        entitity.setInitC2(item.getInitC2());
        entitity.setInitGstoArch(item.getInitGstoArch());
        entitity.setInitGstoInd(item.getInitGstoInd());
        entitity.setInitTotalCost(item.getInitTotalCost());
        entitity.setInitProdBrut(item.getInitProdBrut());
        entitity.setInitCostByPesos(item.getInitCostByPesos());
        entitity.setMonthHours(item.getMonthHours());
        entitity.setMonthScaleSalary(item.getMonthScaleSalary());
        entitity.setMonthVacation(item.getMonthVacation());
        entitity.setMonthFot(item.getMonthFot());
        entitity.setMonthFuel(item.getMonthFuel());
        entitity.setMonthHotel(item.getMonthHotel());
        entitity.setMonthC1(item.getMonthC1());
        entitity.setMonthC2(item.getMonthC2());
        entitity.setMonthGstoArch(item.getMonthGstoArch());
        entitity.setMonthGstoInd(item.getMonthGstoInd());
        entitity.setMonthTotalCost(item.getMonthTotalCost());
        entitity.setMonthProdBrut(item.getMonthProdBrut());
        entitity.setMonthCostByPesos(item.getMonthCostByPesos());
        entitity.setLastUpdateTime(dateData.today());
        entitity.setMonthGstoResult(item.getMonthGstoResult());
        return entitity;
    }

    public MercantilCostos mapToMercantilCostosModel(SalesCostosModel item) {
        return MercantilCostos.builder()
                .initHours(item.getInitHours())
                .initScaleSalary(item.getInitScaleSalary())
                .initVacation(item.getInitVacation())
                .initFot(item.getInitFot())
                .initFuel(item.getInitFuel())
                .initHotel(item.getInitHotel())
                .initC1(item.getInitC1())
                .initC2(item.getInitC2())
                .initGstoArch(item.getInitGstoArch())
                .initGstoInd(item.getInitGstoInd())
                .initTotalCost(item.getInitTotalCost())
                .initProdMer(item.getInitProdMerc())
                .initCostByPesos(item.getInitCostByPesos())
                .monthHours(item.getMonthHours())
                .monthScaleSalary(item.getMonthScaleSalary())
                .monthVacation(item.getMonthVacation())
                .monthFot(item.getMonthFot())
                .monthFuel(item.getMonthFuel())
                .monthHotel(item.getMonthHotel())
                .monthC1(item.getMonthC1())
                .monthC2(item.getMonthC2())
                .monthGstoArch(item.getMonthGstoArch())
                .monthGstoInd(item.getMonthGstoInd())
                .monthTotalCost(item.getMonthTotalCost())
                .monthProdMerc(item.getMonthProdMerc())
                .monthCostByPesos(item.getMonthCostByPesos())
                .lastUpdateTime(dateData.today())
                .creationDate(dateData.currentMonth())
                .activityId(item.getActivityId())
                .monthGstoResult(item.getMonthGstoResult())
                .build();
    }

    public MercantilCostos updateValueInMercantilCostosModel(MercantilCostos entitity, SalesCostosModel item) {
        entitity.setInitHours(item.getInitHours());
        entitity.setInitScaleSalary(item.getInitScaleSalary());
        entitity.setInitVacation(item.getInitVacation());
        entitity.setInitFot(item.getInitFot());
        entitity.setInitFuel(item.getInitFuel());
        entitity.setInitHotel(item.getInitHotel());
        entitity.setInitC1(item.getInitC1());
        entitity.setInitC2(item.getInitC2());
        entitity.setInitGstoArch(item.getInitGstoArch());
        entitity.setInitGstoInd(item.getInitGstoInd());
        entitity.setInitTotalCost(item.getInitTotalCost());
        entitity.setInitProdMer(item.getInitProdMerc());
        entitity.setInitCostByPesos(item.getInitCostByPesos());
        entitity.setMonthHours(item.getMonthHours());
        entitity.setMonthScaleSalary(item.getMonthScaleSalary());
        entitity.setMonthVacation(item.getMonthVacation());
        entitity.setMonthFot(item.getMonthFot());
        entitity.setMonthFuel(item.getMonthFuel());
        entitity.setMonthHotel(item.getMonthHotel());
        entitity.setMonthC1(item.getMonthC1());
        entitity.setMonthC2(item.getMonthC2());
        entitity.setMonthGstoArch(item.getMonthGstoArch());
        entitity.setMonthGstoInd(item.getMonthGstoInd());
        entitity.setMonthTotalCost(item.getMonthTotalCost());
        entitity.setMonthProdMerc(item.getMonthProdMerc());
        entitity.setMonthCostByPesos(item.getMonthCostByPesos());
        entitity.setLastUpdateTime(dateData.today());
        entitity.setMonthProdMerc(item.getMonthProdMerc());
        return entitity;
    }


    public SalesCostosListItem mapToInMercantilCostos(MercantilCostos item) {
        ActivityListItem activityListItem = findActivityById(item.getActivityId());
        return SalesCostosListItem.builder()
                .id(item.getId())
                .initHours(item.getInitHours())
                .initScaleSalary(item.getInitScaleSalary())
                .initVacation(item.getInitVacation())
                .initFot(item.getInitFot())
                .initFuel(item.getInitFuel())
                .initHotel(item.getInitHotel())
                .initC1(item.getInitC1())
                .initC2(item.getInitC2())
                .initGstoArch(item.getInitGstoArch())
                .initGstoInd(item.getInitGstoInd())
                .initTotalCost(item.getInitTotalCost())
                .initProdMerc(item.getInitProdMer())
                .initCostByPesos(item.getInitCostByPesos())
                .monthHours(item.getMonthHours())
                .monthScaleSalary(item.getMonthScaleSalary())
                .monthVacation(item.getMonthVacation())
                .monthFot(item.getMonthFot())
                .monthFuel(item.getMonthFuel())
                .monthHotel(item.getMonthHotel())
                .monthC1(item.getMonthC1())
                .monthC2(item.getMonthC2())
                .monthGstoArch(item.getMonthGstoArch())
                .monthGstoInd(item.getMonthGstoInd())
                .monthTotalCost(item.getMonthTotalCost())
                .monthProdMerc(item.getMonthProdMerc())
                .monthCostByPesos(item.getMonthCostByPesos())
                .lastUpdateTime(item.getLastUpdateTime())
                .activity(activityListItem)
                .monthGstoResult(item.getMonthGstoResult())
                .build();
    }


    public GastArchivoListItem mapGastoArchivoListItem(GastArchReport model) {
        ActivityListItem activity = findActivityById(model.getActivityId());
        return GastArchivoListItem.builder()
                .valor(model.getValor())
                .coef(model.getCoef())
                .lastUpdateTime(model.getLastUpdateTime())
                .activity(activity)
                .importe(model.getImporte())
                .creationDate(model.getCreationDate())
                .lastUpdateTime(model.getLastUpdateTime())
                .build();
    }

    public GastArchReport mapGastoArchivotToModel(GastArchivoListModel model) {
        return GastArchReport.builder()
                .valor(model.getValor())
                .coef(model.getCoef())
                .lastUpdateTime(model.getLastUpdateTime())
                .activityId(model.getActivityId())
                .importe(model.getImporte())
                .creationDate(model.getCreationDate())
                .lastUpdateTime(model.getLastUpdateTime())
                .build();
    }

    public GastResultadoListItem mapGastoResultListItem(GastResultadoReport model) {
        ActivityListItem activity = findActivityById(model.getActivityId());
        return GastResultadoListItem.builder()
                .saleValue(model.getSaleValue())
                .coef(model.getCoef())
                .lastUpdateTime(model.getLastUpdateTime())
                .activity(activity)
                .importe(model.getImporte())
                .creationDate(model.getCreationDate())
                .lastUpdateTime(model.getLastUpdateTime())
                .build();
    }

    public GastResultadoReport mapGastoResultToModel(GastResultadoListModel model) {
        return GastResultadoReport.builder()
                .saleValue(model.getSaleValue())
                .coef(model.getCoef())
                .lastUpdateTime(model.getLastUpdateTime())
                .activityId(model.getActivityId())
                .importe(model.getImporte())
                .creationDate(model.getCreationDate())
                .lastUpdateTime(model.getLastUpdateTime())
                .build();
    }

    public GastIndListItem mapGastoIndListItem(GastIndReport model) {
        ActivityListItem activity = findActivityById(model.getActivityId());
        return GastIndListItem.builder()
                .scaleSalary(model.getScaleSalary())
                .coef(model.getCoef())
                .lastUpdateTime(model.getLastUpdateTime())
                .activity(activity)
                .importe(model.getImporte())
                .creationDate(model.getCreationDate())
                .lastUpdateTime(model.getLastUpdateTime())
                .build();
    }

    public GastIndReport mapGastoIndToModel(GastIndListModel model) {
        return GastIndReport.builder()
                .scaleSalary(model.getScaleSalary())
                .coef(model.getCoef())
                .lastUpdateTime(model.getLastUpdateTime())
                .activityId(model.getActivityId())
                .importe(model.getImporte())
                .creationDate(model.getCreationDate())
                .lastUpdateTime(model.getLastUpdateTime())
                .build();
    }


    public ActSalaryReport mapFromActSalaryListModel(ActSalaryListModel model) {
        return ActSalaryReport.builder()
                .scaleSalary(model.getScaleSalary())
                .totalSalary(model.getTotalSalary())
                .otherSalary(model.getOtherSalary())
                .production(model.getProduction())
                .lastUpdateTime(model.getLastUpdateTime())
                .activityId(model.getActivityId())
                .vacation(model.getVacation())
                .creationDate(model.getCreationDate())
                .lastUpdateTime(model.getLastUpdateTime())
                .build();
    }

    public ActSalaryListItem mapFromActSalaryReport(ActSalaryReport model) {
        ActivityListItem activity = findActivityById(model.getActivityId());
        return ActSalaryListItem.builder()
                .id(model.getId())
                .scaleSalary(model.getScaleSalary())
                .totalSalary(model.getTotalSalary())
                .otherSalary(model.getOtherSalary())
                .production(model.getProduction())
                .lastUpdateTime(model.getLastUpdateTime())
                .activity(activity)
                .vacation(model.getVacation())
                .build();
    }

    public ActivityListItem findActivityById(String id) {
        return hiringUtilsService.findById(id);
    }


}
