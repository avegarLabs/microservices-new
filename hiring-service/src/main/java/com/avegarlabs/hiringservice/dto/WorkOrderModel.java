package com.avegarlabs.hiringservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkOrderModel {
    private String code;
    private String description;
    private String projectId;
}
