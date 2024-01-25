package com.avegarlabs.facturationsalesservice.services;


import com.avegarlabs.facturationsalesservice.dto.*;
import com.avegarlabs.facturationsalesservice.models.Currency;
import com.avegarlabs.facturationsalesservice.models.Invoice;
import com.avegarlabs.facturationsalesservice.models.Sale;
import com.avegarlabs.facturationsalesservice.repositories.CurrencyRecpository;
import com.avegarlabs.facturationsalesservice.repositories.InvoiceRepository;
import com.avegarlabs.facturationsalesservice.repositories.SaleRepository;
import com.avegarlabs.facturationsalesservice.utils.DateData;
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
public class SalesService {
    private final CurrencyRecpository currencyRecpository;
    private final SaleRepository repository;
    private final DateData dateData;
    private final CurrencyService currencyService;
    private final HiringUtilsService hiringUtilsService;
    private final InvoiceRepository invoiceRepository;
    private final HrUtilService hrUtilService;


    public List<ByServicesStaticsListItem> getResumeByServices() {
        List<DepartamentListItem> departaments = hrUtilService.getDepartamentList().getDepartamentos();
        List<SaleListItem> dataList = findInCurrentMonth();
        List<ByServicesStaticsListItem> staticsListItemList = new ArrayList<>();
        for (DepartamentListItem departament : departaments) {
            List<SaleListItem> filterList = dataList.parallelStream().filter(item -> item.getActivity().getWorkOrder().getProject().getDepartament().getId() == departament.getId()).toList();
            if (filterList.size() > 0) {
                List<ServicesTypesStaticsListItem> calcList = getSaleByServices(filterList);
                staticsListItemList.add(new ByServicesStaticsListItem(departament, calcList));
            }
        }
        return staticsListItemList;
    }

    private List<ServicesTypesStaticsListItem> getSaleByServices(List<SaleListItem> dataList) {
        List<ActTypeListItem> servicesTypes = hrUtilService.getTypesList().getTipos();
        List<ServicesTypesStaticsListItem> resultList = new ArrayList<>();
        for (ActTypeListItem types : servicesTypes) {
            ServicesTypesStaticsListItem item = calSaleInTypeOfServices(types, dataList);
            if (item != null) {
                resultList.add(item);
            }
        }

        return resultList;
    }


    private ServicesTypesStaticsListItem calSaleInTypeOfServices(ActTypeListItem type, List<SaleListItem> dataList) {
        double proVal = 0.0;
        proVal = calcSumSaleType(type.getId(), dataList);
        return proVal != 0.0 ? new ServicesTypesStaticsListItem(type, new BigDecimal(proVal)) : null;
    }

    private double calcSumSaleType(int id, List<SaleListItem> pr3List) {
        return pr3List.parallelStream().filter(item -> item.getActivity().getActType() == id).map(itemm -> itemm.getValue().doubleValue()).reduce(0.0, Double::sum);

    }


    public List<DepartamentStaticsListItem> getSaleByDepartaments() {
        List<DepartamentListItem> departaments = hrUtilService.getDepartamentList().getDepartamentos();
        List<SaleListItem> dataList = findInCurrentMonth();
        List<DepartamentStaticsListItem> resultList = new ArrayList<>();
        for (DepartamentListItem departament : departaments) {
            DepartamentStaticsListItem item = calSaleInDepartament(departament, dataList);
            if (item != null) {
                resultList.add(item);
            }
        }
        return resultList;
    }

    private DepartamentStaticsListItem calSaleInDepartament(DepartamentListItem departament, List<SaleListItem> dataList) {
        double proVal = 0.0;
        proVal = calcSumSaleDept(departament.getId(), dataList);
        return proVal != 0.0 ? new DepartamentStaticsListItem(departament, new BigDecimal(proVal)) : null;
    }

    private double calcSumSaleDept(int departamentId, List<SaleListItem> pr3List) {
        return pr3List.parallelStream().filter(item -> item.getActivity().getWorkOrder().getProject().getServiceRequest().getDirectionId() == departamentId).map(itemm -> itemm.getValue().doubleValue()).reduce(0.0, Double::sum);
    }


    public List<SaleListItem> getAll() {
        return repository.findAll().stream().map(this::mapSaleListItemToSale).collect(Collectors.toList());
    }

    public List<SaleListItem> getAllToGlobal() {
        return repository.findAll().parallelStream().filter(item -> !item.getCreationDate().trim().equals(dateData.currentMonth())).map(this::mapSaleListItemToSale).collect(Collectors.toList());
    }

    public List<SaleListItem> findInCurrentMonth() {
        List<Sale> list = repository.findAll().parallelStream().filter(item -> item.getCreationDate().trim().equals(dateData.currentMonth())).toList();
        return list.stream().map(this::mapSaleListItemToSale).collect(Collectors.toList());
    }

    public String saveSaleInBatch(SalePostModel salePostModel) {
        List<Sale> sales = salePostModel.getSaleList().stream().map(this::mapSaleToSaleModel).toList();
        repository.saveAll(sales);
        for (SaleModel saleModel : salePostModel.getSaleList()) {
            updateInvoiceState(saleModel.getActivityId(), saleModel.getInvoiceNumber());
        }
        return "Done";
    }

    private void updateInvoiceState(String activityId, String number) {
        Invoice invoice = invoiceRepository.findAll().parallelStream().filter(item -> item.getActivityId().trim().equals(activityId) && item.getInvoiceNumber().trim().equals(number) && item.getCreationDate().trim().equals(dateData.currentMonth())).findFirst().get();
        invoice.setSale(true);
        invoiceRepository.save(invoice);
    }


    public SaleListItem addSale(SaleModel model) {
        Sale sale = mapSaleToSaleModel(model);
        repository.save(sale);
        updateInvoiceState(model.getActivityId(), model.getInvoiceNumber());
        return mapSaleListItemToSale(sale);
    }

    public SaleListItem updateSale(String id, SaleModel model) {
        Sale sale = repository.findById(id).get();
        sale.setInvoiceNumber(model.getInvoiceNumber());
        sale.setValue(model.getValue());
        sale.setCreationDate(dateData.currentMonth());
        repository.save(sale);
        return mapSaleListItemToSale(sale);
    }

    public void deleteSale(String id) {
        repository.deleteById(id);
    }


    public SaleListItem mapSaleListItemToSale(Sale sale) {
        log.info("Buscar: ", sale.getInvoiceNumber() + " <--> " + sale.getValue() + " <---> " + sale.getId());
        return SaleListItem.builder()
                .id(sale.getId())
                .invoiceNumber(sale.getInvoiceNumber())
                .value(sale.getValue())
                .state(sale.isState())
                .updateTime(sale.getCreationDate())
                .activity(findActivityListItem(sale.getActivityId()))
                .currency(currencyService.mapCurrencyListItemToCurrencyModel(sale.getCurrency()))
                .build();
    }


    public Sale mapSaleToSaleModel(SaleModel model) {
        Currency currency = currencyRecpository.findById(model.getCurrencyId()).get();
        return Sale.builder()
                .invoiceNumber(model.getInvoiceNumber())
                .state(true)
                .creationDate(dateData.currentMonth())
                .value(model.getValue())
                .activityId(model.getActivityId())
                .currency(currency)
                .build();
    }

    private ActivityListItem findActivityListItem(String id) {
        return hiringUtilsService.getActivityList().parallelStream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    }

    public List<ResumeSaleList> getResumeSale(){
       List<ResumeSaleList> resumeSaleLists = new ArrayList<>();
       List<SaleListItem> saleListItems = repository.findAll().stream().map(this::mapSaleListItemToSale).toList();
       Set<ActivityListItem> activityListItems = saleListItems.parallelStream().map(SaleListItem::getActivity).collect(Collectors.toSet());
       for (ActivityListItem item : activityListItems) {
            List<SaleListItem> filterSalesByActivity = saleListItems.parallelStream().filter(activityListItem -> activityListItem.getActivity().getId().equals(item.getId())).toList();
            double saleValue = calSalesSum(filterSalesByActivity);
            resumeSaleLists.add(new ResumeSaleList(new BigDecimal(saleValue), item));
        }

        return resumeSaleLists;
    }

    private double calSalesSum(List<SaleListItem> filterSalesByActivity) {
        return filterSalesByActivity.stream().map(saleListItem -> saleListItem.getValue().doubleValue()).reduce(0.0, Double::sum);
    }


}
