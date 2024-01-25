package com.avegarlabs.hiringservice.dto;


import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupStaticsModel {
    public String name;
    public Integer countContracts;
    public BigDecimal value;
}
