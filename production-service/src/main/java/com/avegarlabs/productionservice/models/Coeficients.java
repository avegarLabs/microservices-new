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
public class Coeficients {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;
    private BigDecimal prodCoeficient;
    private BigDecimal hoursCoeficient;

    @Temporal(TemporalType.DATE)
    Date creationDate;

    @Temporal(TemporalType.DATE)
    Date lastUpdateTime;

}
