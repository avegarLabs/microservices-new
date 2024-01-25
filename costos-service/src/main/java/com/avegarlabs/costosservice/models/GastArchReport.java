package com.avegarlabs.costosservice.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class GastArchReport {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;
    private BigDecimal valor;
    private double coef;
    private BigDecimal importe;
    private String creationDate;
    @Temporal(TemporalType.DATE)
    Date lastUpdateTime;
    private String activityId;

}
