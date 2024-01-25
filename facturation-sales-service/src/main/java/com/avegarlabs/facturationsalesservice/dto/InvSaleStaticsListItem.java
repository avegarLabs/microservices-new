package com.avegarlabs.facturationsalesservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvSaleStaticsListItem {
    private int factCant;
    private BigDecimal factAmount;
    private int factPendCant;
    private BigDecimal factPendAmount;
    private BigDecimal saleAmount;
    private List<DepartamentStaticsListItem> departamentStatics;
    private List<ServicesTypesStaticsListItem> servicesTypesStatics;
}
