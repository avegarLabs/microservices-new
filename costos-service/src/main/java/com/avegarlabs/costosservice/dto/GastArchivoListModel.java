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
public class GastArchivoListModel {
    private BigDecimal valor;
    private double coef;
    private BigDecimal importe;
    private String creationDate;
    private   Date lastUpdateTime;
    private String activityId;
}
