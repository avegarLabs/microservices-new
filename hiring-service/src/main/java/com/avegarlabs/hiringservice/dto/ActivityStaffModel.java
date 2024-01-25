package com.avegarlabs.hiringservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActivityStaffModel {
    private String activityId;
    private int workerId;
    private BigDecimal hours;
    private BigDecimal value;
}
