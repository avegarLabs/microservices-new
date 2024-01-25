package com.avegarlabs.coninsuservice.dto;

import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConinsuStaticsListItem {
   private List<DepartamentStaticsListItem> departamentStatics;
   private List<ServicesTypesStaticsListItem> servicesTypesStatics;
}
