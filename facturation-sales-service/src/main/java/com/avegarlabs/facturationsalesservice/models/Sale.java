package com.avegarlabs.facturationsalesservice.models;

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
public class Sale {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;
    private String invoiceNumber;
    private BigDecimal value;
    private String creationDate;
    private boolean state;
    private String activityId;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;
}
