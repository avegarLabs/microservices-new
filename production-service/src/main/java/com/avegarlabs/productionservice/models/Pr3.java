package com.avegarlabs.productionservice.models;

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
public class Pr3 {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;
    private BigDecimal hours;
    private BigDecimal extHours;
    private BigDecimal production;
    private BigDecimal productionFixe;
    private String creationDate;

    @Temporal(TemporalType.DATE)
    Date lastUpdateTime;

    private String activityId;
    private int workerId;
}
