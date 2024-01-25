package com.avegarlabs.costosservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pr3Model {
    private BigDecimal hours;
    private BigDecimal extHours;
    private BigDecimal production;
    private BigDecimal productionFixe;
    private String activityId;
    private int workerId;
}
