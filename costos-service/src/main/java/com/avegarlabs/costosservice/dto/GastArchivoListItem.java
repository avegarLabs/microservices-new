package com.avegarlabs.costosservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GastArchivoListItem {
    private String id;
    private BigDecimal valor;
    private double coef;
    private BigDecimal importe;
    private String creationDate;
    Date lastUpdateTime;
    private ActivityListItem activity;
}
