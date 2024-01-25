package com.avegarlabs.costosservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartamentStaticsListItem {
   private DepartamentListItem departament;
   private BigDecimal productionValue;
}
