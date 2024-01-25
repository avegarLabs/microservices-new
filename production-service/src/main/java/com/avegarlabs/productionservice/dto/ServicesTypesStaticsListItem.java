package com.avegarlabs.productionservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServicesTypesStaticsListItem {
   private ActTypeListItem servicesTypes;
   private BigDecimal productionValue;
}
