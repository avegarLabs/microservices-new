package com.avegarlabs.coninsuservice.models;

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
public class Coninsu {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;
    private BigDecimal count;
    private String activityId;
    private String creationDate;

    @Temporal(TemporalType.DATE)
    Date lastUpdateTime;

    @ManyToOne
    @JoinColumn(name = "formater_id", nullable = false)
    private Formaters formater;
}
