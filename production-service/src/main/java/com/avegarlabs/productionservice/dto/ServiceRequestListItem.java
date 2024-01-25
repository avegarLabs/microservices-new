package com.avegarlabs.productionservice.dto;

import lombok.*;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceRequestListItem {
    private String id;
    private String number;
    private String name;
    private Date receptionDate;
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
    private Date approveDate;
    private Date stopedDate;
    private Date resumeDate;
    private Date rejectDate;
    private Date cancelationDate;
    private ClientListItem client;
    private ServiceTypeListItem serviceType;
    private int directionId;
    public String directionRepresentBy;
    public String chargeContractSigning;

    public String creationDate;
    private Date lastUpdateTime;
    private String moniker;
}
