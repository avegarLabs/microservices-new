package com.avegarlabs.costosservice.dto;

import lombok.*;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OtherConceptsListItem {
    private String id;
    private BigDecimal foot;
    private BigDecimal fuel;
    private BigDecimal hotel;
    private BigDecimal c1;
    private BigDecimal c2;
    private Date lastUpdateTime;
    private ActivityListItem activity;
}
