package com.avegarlabs.hiringservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContractsModel {
    private String name;
    private String number;
    private String description;
    private String about;
    private String state;
    private String endDate;
    private String aproveDate;
    private String serviceRequestId;
}
