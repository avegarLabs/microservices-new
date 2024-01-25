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
public class GastIndListModel {
    private BigDecimal scaleSalary;
    private double coef;
    private BigDecimal importe;
    private String creationDate;
    private   Date lastUpdateTime;
    private String activityId;
}
