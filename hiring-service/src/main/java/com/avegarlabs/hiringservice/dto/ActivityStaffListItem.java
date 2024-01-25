package com.avegarlabs.hiringservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActivityStaffListItem {
    private String id;
    private ActivityListItem activityListItem;
    private WorkerListItem workerListItem;
    private BigDecimal hours;
    private BigDecimal value;
}
