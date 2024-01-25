package com.avegarlabs.facturationsalesservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CurrencyListItem {
    private String id;
    private String name;
}
