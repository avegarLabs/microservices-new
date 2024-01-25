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
public class GastIndListItem {
    private String id;
    private BigDecimal scaleSalary;
    private double coef;
    private BigDecimal importe;
    private String creationDate;
    Date lastUpdateTime;
    private ActivityListItem activity;
}
