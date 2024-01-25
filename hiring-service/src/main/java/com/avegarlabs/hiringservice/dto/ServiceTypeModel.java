package com.avegarlabs.hiringservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceTypeModel {
    private String id;
    private String code;
    private String description;
}
