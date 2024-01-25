package com.avegarlabs.productionservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pr3ListItem {
    private String id;
    private BigDecimal hours;
    private BigDecimal extHours;
    private BigDecimal production;
    private BigDecimal productionFixe;
    private ActivityListItem activityListItem;
    private WorkerListItem worker;
    private Date lastUpdateTime;
    private String creationDate;
}
