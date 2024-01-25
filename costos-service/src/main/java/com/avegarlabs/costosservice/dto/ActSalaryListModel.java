package com.avegarlabs.costosservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActSalaryListModel {
    private BigDecimal scaleSalary;
    private BigDecimal totalSalary;
    private BigDecimal otherSalary;
    private BigDecimal vacation;
    private BigDecimal production;
    private Date lastUpdateTime;
    private String creationDate;
    private String activityId;
}
