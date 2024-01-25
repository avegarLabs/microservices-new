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
public class OtherConceptsModel {
    private BigDecimal foot;
    private BigDecimal fuel;
    private BigDecimal hotel;
    private BigDecimal c1;
    private BigDecimal c2;
    private String activityId;
}
