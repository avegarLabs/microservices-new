package com.avegarlabs.facturationsalesservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvoiceListItem {
    private String id;
    private String invoiceNumber;
    private BigDecimal value;
    private ActivityListItem activity;
    private CurrencyListItem currency;
    private boolean state;
    private boolean isSale;
    private Date lastUpdateTime;

}
