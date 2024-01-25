package com.avegarlabs.hiringservice.dto;

import lombok.*;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContractsListItem {
    private String id;
    private String name;
    private String number;
    private String description;
    private String about;
    private String state;
    private Date endDate;
    private Date aproveDate;
    private ServiceRequestListItem serviceRequest;
    private String moniker;
}
