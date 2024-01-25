package com.avegarlabs.costosservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FixePr3ListItem {
    private BigDecimal fixValue;
    private BigDecimal production;
    private BigDecimal sale;
    private BigDecimal sumProd;
    private BigDecimal SumSale;
    private ActivityListItem activityListItem;
}
