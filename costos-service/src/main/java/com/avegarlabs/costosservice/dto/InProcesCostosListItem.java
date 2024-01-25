package com.avegarlabs.costosservice.dto;

import lombok.*;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InProcesCostosListItem {
    private String id;
    private BigDecimal initHours;
    private BigDecimal initScaleSalary;
    private BigDecimal initVacation;
    private BigDecimal initFot;
    private BigDecimal initFuel;
    private BigDecimal initHotel;
    private BigDecimal initC1;
    private BigDecimal initC2;
    private BigDecimal initGstoArch;
    private BigDecimal initGstoInd;
    private BigDecimal initTotalCost;
    private BigDecimal initProdBrut;
    private BigDecimal initCostByPesos;
    private BigDecimal monthHours;
    private BigDecimal monthScaleSalary;
    private BigDecimal monthVacation;
    private BigDecimal monthFot;
    private BigDecimal monthFuel;
    private BigDecimal monthHotel;
    private BigDecimal monthC1;
    private BigDecimal monthC2;
    private BigDecimal monthGstoArch;
    private BigDecimal monthGstoInd;
    private BigDecimal monthTotalCost;
    private BigDecimal monthProdBrut;
    private BigDecimal monthCostByPesos;
    private BigDecimal monthGstoResult;
    private Date lastUpdateTime;
    private ActivityListItem activity;
}
