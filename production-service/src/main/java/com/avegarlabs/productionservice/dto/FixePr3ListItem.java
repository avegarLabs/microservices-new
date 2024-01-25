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
public class FixePr3ListItem {
    private BigDecimal fixValue;
    private BigDecimal production;
    private BigDecimal sale;
    private BigDecimal sumProd;
    private BigDecimal SumSale;
    private ActivityListItem activityListItem;
}
