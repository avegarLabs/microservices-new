package com.avegarlabs.hiringservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class ServiceRequest {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;
    private String number;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date receptionDate;
    @Temporal(TemporalType.DATE)
    private Date techPrepDate;
    private Boolean includeOffer;
    private Boolean byContract;
    private String representBy;
    private String resolution;
    private String accord;
    public String nameOfContractSigning;
    public String engineeringService;
    public String other;
    public String another;
    public String description;
    public Boolean studyArea;
    public Boolean queryOrganism;
    public Boolean microCertification;
    public Boolean projectionTask;
    public Boolean technicalTask;
    public Boolean digitalInfo;
    public Boolean planes;
    public Boolean constructionLicense;
    public Boolean environmentalLicense;
    public Boolean ecologicalEstimation;
    public String othersDocs;
    public String state;
    public String approveFor;
    public String workOrder;
    public String observations;
    public String contractNumber;
    @Temporal(TemporalType.DATE)
    private Date approveDate;
    @Temporal(TemporalType.DATE)
    private Date stopedDate;
    @Temporal(TemporalType.DATE)
    private Date resumeDate;
    @Temporal(TemporalType.DATE)
    private Date rejectDate;
    @Temporal(TemporalType.DATE)
    private Date cancelationDate;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Clients client;
    @ManyToOne
    @JoinColumn(name = "service_type_id", nullable = false)
    private ServiceType serviceType;
    private int directionId;
    public String directionRepresentBy;
    public String chargeContractSigning;

    public String creationDate;
    @Temporal(TemporalType.DATE)
    private Date lastUpdateTime;
    private String moniker;
   }
