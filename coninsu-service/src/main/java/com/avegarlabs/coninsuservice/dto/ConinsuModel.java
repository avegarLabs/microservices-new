package com.avegarlabs.coninsuservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConinsuModel {
    private BigDecimal count;
    private String activityId;
    private String formaterId;

}
