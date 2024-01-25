package com.avegarlabs.hiringservice.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Activity {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;
    private String code;
    private String description;
    private BigDecimal contractValue;
    private Boolean active;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private WorkOrder order;
    private int actType;

    @OneToMany(mappedBy="activity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ActivityStaff> activityStaffs;
}
