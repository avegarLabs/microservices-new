package com.avegarlabs.costosservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServicesTypesStaticsItem {
   private BigDecimal salaryTotal;
   private BigDecimal salaryMonth;
   private BigDecimal archTotal;
   private BigDecimal archMonth;
   private BigDecimal indTotal;
   private BigDecimal indMonth;
   private BigDecimal resultTotal;
   private BigDecimal resultMonth;
}
