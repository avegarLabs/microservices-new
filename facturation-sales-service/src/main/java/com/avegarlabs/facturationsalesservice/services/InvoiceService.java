package com.avegarlabs.facturationsalesservice.services;


import com.avegarlabs.facturationsalesservice.dto.*;
import com.avegarlabs.facturationsalesservice.models.Currency;
import com.avegarlabs.facturationsalesservice.models.Invoice;
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
public class InvoiceService {
   private final CurrencyRecpository currencyRecpository;
    private final InvoiceRepository repository;
    private final DateData dateData;
    private final CurrencyService currencyService;
    private final HiringUtilsService hiringUtilsService;
    private final HrUtilService hrUtilService;
    private final SaleRepository saleRepository;
    private final SalesService salesService;


    public InvSaleStaticsListItem getStatics(){
        return InvSaleStaticsListItem.builder()
                 .factCant(getAll().parallelStream().filter(item -> item.isState()).toList().size())
                .factAmount(getInvoiceAmount())
                .factPendCant(findInvoicePend())
                .factPendAmount(getInvoicePendAmount())
                .saleAmount(calcAmountTotal())
                .departamentStatics(getSaleByDepartaments())
                .servicesTypesStatics(getSaleByServices())
                .build();
    }

    private List<ServicesTypesStaticsListItem> getSaleByServices() {
        List<ActTypeListItem> servicesTypes = hrUtilService.getTypesList().getTipos();
        List<SaleListItem> dataList = saleRepository.findAll().stream().map(salesService::mapSaleListItemToSale).toList();
        List<ServicesTypesStaticsListItem> resultList = new ArrayList<>();
        for (ActTypeListItem types : servicesTypes) {
            ServicesTypesStaticsListItem item = calSaleInTypeOfServices(types, dataList);
            if(item != null) {
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


    private List<DepartamentStaticsListItem> getSaleByDepartaments() {
        List<DepartamentListItem> departaments = hrUtilService.getDepartamentList().getDepartamentos();
        List<SaleListItem> dataList = saleRepository.findAll().stream().map(salesService::mapSaleListItemToSale).toList();
        List<DepartamentStaticsListItem> resultList = new ArrayList<>();
        for (DepartamentListItem departament : departaments) {
            DepartamentStaticsListItem item = calSaleInDepartament(departament, dataList);
            if(item != null) {
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

    private double calcSumSaleDept(int departamentId, List<SaleListItem> pr3List){
        return pr3List.parallelStream().filter(item -> item.getActivity().getWorkOrder().getProject().getServiceRequest().getDirectionId() == departamentId).map(itemm -> itemm.getValue().doubleValue()).reduce(0.0, Double::sum);
    }


    private BigDecimal calcAmountTotal() {
        double saleTotal = saleRepository.findAll().stream().map(item -> item.getValue().doubleValue()).reduce(0.0, Double::sum);
        return new BigDecimal(saleTotal);
    }

    private BigDecimal getInvoicePendAmount() {
        double val = getAll().parallelStream().filter(item -> !item.isSale()).map(item -> item.getValue().doubleValue()).reduce(0.0, Double::sum);
        return new BigDecimal(val);
    }

    private int findInvoicePend() {
        return getAll().parallelStream().filter(item -> !item.isSale()).toList().size();
    }

    private BigDecimal getInvoiceAmount() {
        double total = getAll().parallelStream().filter(item -> item.isState()).map(item -> item.getValue().doubleValue()).reduce(0.0, Double::sum);
        return new BigDecimal(total);
    }


    public List<InvoiceListItem> getAll(){
        return repository.findAll().stream().map(this::mapInvoiceListItemToInvoice).collect(Collectors.toList());
    }

    public List<InvoiceListItem> getAllToGlobal(){
        return repository.findAll().parallelStream().filter(item -> !item.getCreationDate().trim().equals(dateData.currentMonth())).map(this::mapInvoiceListItemToInvoice).collect(Collectors.toList());
    }

    public List<InvoiceListItem> findInCurrentMonth(){
        List<Invoice> list = repository.findAll().parallelStream().filter(item -> item.creationDate.trim().equals(dateData.currentMonth())).toList();
        return list.stream().map(this::mapInvoiceListItemToInvoice).collect(Collectors.toList());
    }


    public InvoiceListItem addInvoice(InvoiceModel model){
        Invoice invoice = mapInvoiceToInvoiceModel(model);
        repository.save(invoice);
        return mapInvoiceListItemToInvoice(invoice);
    }

    public InvoiceListItem updateInvoice(String id, InvoiceModel model){
        Invoice invoice = repository.findById(id).get();
        invoice.setInvoiceNumber(model.getInvoiceNumber());
        invoice.setValue(model.getValue());
        invoice.setLastUpdateTime(dateData.today());
        repository.save(invoice);
        return mapInvoiceListItemToInvoice(invoice);
    }

    public InvoiceListItem deleteInvoice(String id){
        Invoice invoice = repository.findById(id).get();
        invoice.setState(false);
        repository.save(invoice);
        return mapInvoiceListItemToInvoice(invoice);
    }




    public InvoiceListItem mapInvoiceListItemToInvoice(Invoice invoice){
       ActivityListItem activityListItem = findActivityListItem(invoice.getActivityId());
       if(activityListItem == null){
           System.out.println(invoice.getActivityId() + "Data");
       }
       return InvoiceListItem.builder()
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .value(invoice.getValue())
                .state(invoice.isState())
                .lastUpdateTime(invoice.getLastUpdateTime())
                .activity(activityListItem)
                .isSale(invoice.isSale())
                .currency(currencyService.mapCurrencyListItemToCurrencyModel(invoice.getCurrency()))
                .build();
       }


    public Invoice mapInvoiceToInvoiceModel(InvoiceModel model){
       Currency currency = currencyRecpository.findById(model.getCurrencyId()).get();
        return Invoice.builder()
                .invoiceNumber(model.getInvoiceNumber())
                .state(true)
                .lastUpdateTime(dateData.today())
                .creationDate(dateData.currentMonth())
                .value(model.getValue())
                .activityId(model.getActivityId())
                .currency(currency)
                .isSale(false)
                .build();
    }

    private ActivityListItem findActivityListItem(String id){
        return hiringUtilsService.getActivityList().parallelStream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    }

}
