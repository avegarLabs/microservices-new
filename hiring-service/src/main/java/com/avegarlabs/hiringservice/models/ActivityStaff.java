package com.avegarlabs.hiringservice.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class ActivityStaff {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;
    private int workerId;
    private BigDecimal hours;
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;
}
