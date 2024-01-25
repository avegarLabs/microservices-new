package com.avegarlabs.facturationsalesservice.dto;

import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActivityListResponse {
    private List<ActivityListItem> activities;
}
