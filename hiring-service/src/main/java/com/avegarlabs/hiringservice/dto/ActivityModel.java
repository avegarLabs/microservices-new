package com.avegarlabs.hiringservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActivityModel {
    private String code;
    private String description;
    private BigDecimal contractValue;
    private String workOrderId;
    private int actType;
    private Boolean active;

}
