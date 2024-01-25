package com.avegarlabs.productionservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pr3StaticsListItem {
   private List<DepartamentStaticsListItem> departamentStatics;
   private List<ServicesTypesStaticsListItem> servicesTypesStatics;
}
