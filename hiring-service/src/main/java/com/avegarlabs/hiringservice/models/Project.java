package com.avegarlabs.hiringservice.models;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Project {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;
    private String name;
    private String shortName;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date initialDateEstimate;
    @Temporal(TemporalType.DATE)
    private Date initialDateReal;
    @Temporal(TemporalType.DATE)
    private Date initialDateAdjust;
    @Temporal(TemporalType.DATE)
    private Date initialCurrentScope;
    private String duration;
    private String durationAdjust;
    private String durationCurrentScope;
    @Temporal(TemporalType.DATE)
    private Date endDateEstimate;
    @Temporal(TemporalType.DATE)
    private Date endDateReal;
    @Temporal(TemporalType.DATE)
    private Date endDateAdjust;
    @Temporal(TemporalType.DATE)
    private Date endCurrentScope;
    private String membersCount;
    private String planesCount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_request_id")
    private ServiceRequest serviceRequest;

    @OneToMany(mappedBy="project", cascade = CascadeType.ALL)
    private Set<WorkOrder> workOrders;

    public String creationDate;
    @Temporal(TemporalType.DATE)
    private Date lastUpdateTime;
    private String moniker;
}
