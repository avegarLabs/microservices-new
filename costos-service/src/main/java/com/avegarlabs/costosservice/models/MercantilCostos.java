package com.avegarlabs.costosservice.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class MercantilCostos {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
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
    private BigDecimal initProdMer;
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
    private BigDecimal monthProdMerc;
    private BigDecimal monthCostByPesos;
    private BigDecimal monthGstoResult;

    private String creationDate;
    @Temporal(TemporalType.DATE)
    Date lastUpdateTime;
    private String activityId;

}
