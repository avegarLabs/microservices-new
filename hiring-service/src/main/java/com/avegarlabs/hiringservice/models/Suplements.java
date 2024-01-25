package com.avegarlabs.hiringservice.models;

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
public class Suplements {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;
    private String number;
    private String description;
    private String about;
    private String state;
    public String creationDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Temporal(TemporalType.DATE)
    private Date aproveDate;
    private BigDecimal value;
}
