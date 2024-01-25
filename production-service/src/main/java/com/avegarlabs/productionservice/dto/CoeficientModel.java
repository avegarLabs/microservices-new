package com.avegarlabs.productionservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CoeficientModel {
    private BigDecimal prodCoeficient;
    private BigDecimal hoursCoeficient;
}
