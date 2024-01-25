package com.avegarlabs.facturationsalesservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResumeSaleList {
		private BigDecimal value;
		private ActivityListItem activity;

}
