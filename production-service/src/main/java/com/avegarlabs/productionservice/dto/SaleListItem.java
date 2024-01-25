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
public class SaleListItem {
	private String id;
	private String invoiceNumber;
	private BigDecimal value;
	private ActivityListItem activity;
	private CurrencyListItem currency;
	private boolean state;
	private String updateTime;
}
