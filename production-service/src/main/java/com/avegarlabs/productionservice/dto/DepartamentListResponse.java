package com.avegarlabs.productionservice.dto;

import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartamentListResponse {
    private List<DepartamentListItem> departamentos;
}
