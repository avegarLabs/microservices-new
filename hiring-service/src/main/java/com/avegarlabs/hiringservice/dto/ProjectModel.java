package com.avegarlabs.hiringservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectModel {
    private String id;
    private String name;
    private String shortName;
    private String description;
    private String initialDateEstimate;
    private String initialDateReal;
    private String initialDateAdjust;
    private String initialCurrentScope;
    private String duration;
    private String durationAdjust;
    private String durationCurrentScope;
    private String endDateEstimate;
    private String endDateReal;
    private String endDateAdjust;
    private String endCurrentScope;
    private String membersCount;
    private String planesCount;
    private String serviceRequestId;
}
