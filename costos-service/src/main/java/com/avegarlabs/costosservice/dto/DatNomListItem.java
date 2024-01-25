package com.avegarlabs.costosservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DatNomListItem {
    private String ci;
    private  String codigo_interno;
    private BigDecimal hours;
    private BigDecimal salary;
    private BigDecimal rate;
}
