package com.avegarlabs.hiringservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContractsControlResponse {
    private  String contractNumber;
    private String workOrder;
    private BigDecimal contractValue;
    private BigDecimal production;
    private BigDecimal sales;
    private  BigDecimal rest;
}
