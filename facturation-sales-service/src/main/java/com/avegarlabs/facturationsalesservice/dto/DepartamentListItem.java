package com.avegarlabs.facturationsalesservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartamentListItem {
    private int id;
    private String code;
    private String name;
}
