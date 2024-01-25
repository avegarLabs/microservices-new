package com.avegarlabs.productionservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActivityListItem {
    private String id;
    private String code;
    private String description;
    private BigDecimal contractValue;
    private int actType;
    private WorkOrderListItem workOrder;
    private Boolean active;
    private String name;
}
