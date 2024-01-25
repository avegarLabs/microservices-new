package com.avegarlabs.costosservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkOrderListItem {
    private String id;
    private String code;
    private String description;
    private ProjectListItem project;
 }
