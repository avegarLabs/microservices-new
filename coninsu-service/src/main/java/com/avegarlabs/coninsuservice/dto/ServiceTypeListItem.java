package com.avegarlabs.coninsuservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceTypeListItem {
    private String id;
    private String code;
    private String description;
}
