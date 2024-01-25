package com.avegarlabs.hiringservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceRequestModel {
    private String number;
    private String name;
    private String receptionDate;
    private String techPrepDate;
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
    private String approveDate;
    private String stopedDate;
    private String resumeDate;
    private String rejectDate;
    private String cancelationDate;
    private String clientId;
    private String serviceTypeId;
    private int directionId;
    public String directionRepresentBy;
    public String chargeContractSigning;
}
