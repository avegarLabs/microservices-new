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
public class ByServicesStaticsListItem {
   private DepartamentListItem departament;
   private List<ServicesTypesStaticsListItem> listItems;
}
