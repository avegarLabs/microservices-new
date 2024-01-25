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
public class ConinsuListItem {
    private String id;
    private BigDecimal count;
    private ActivityListItem activity;
    private FormaterListItem formater;
    private Date lastUpdateTime;
}
    