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
public class ActSalaryListItem {
    private String id;
    private BigDecimal scaleSalary;
    private BigDecimal totalSalary;
    private BigDecimal otherSalary;
    private BigDecimal vacation;
    private BigDecimal production;
    private Date lastUpdateTime;
    private ActivityListItem activity;
}
