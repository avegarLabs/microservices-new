package com.avegarlabs.costosservice.dto;

import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pr3StaticsListItem {
   private List<DepartamentStaticsListItem> departamentStatics;
   private List<ServicesTypesStaticsItem> servicesTypesStatics;
}
