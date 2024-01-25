package com.avegarlabs.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NomQueryItem {
    private String ci;
    private  String codigo;
    private BigDecimal totalCobrar;
    private BigDecimal totalTiempoEntrado;
    private BigDecimal totalBonificaciones;
    private BigDecimal totalPenalizaciones;
}
