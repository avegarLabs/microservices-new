package com.avegarlabs.facturationsalesservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaleModel {
    private String invoiceNumber;
    private BigDecimal value;
    private String activityId;
    private String currencyId;

}
