package com.avegarlabs.productionservice.dto;

import lombok.*;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectListItem {
    private String id;
    private String name;
    private String shortName;
    private String description;
    private Date initialDateEstimate;
    private Date initialDateReal;
    private Date initialDateAdjust;
    private Date initialCurrentScope;
    private String duration;
    private String durationAdjust;
    private String durationCurrentScope;
    private Date endDateEstimate;
    private Date endDateReal;
    private Date endDateAdjust;
    private Date endCurrentScope;
    private String membersCount;
    private String planesCount;
    private ServiceRequestListItem serviceRequest;
    private DepartamentListItem departament;
    private String moniker;
}
