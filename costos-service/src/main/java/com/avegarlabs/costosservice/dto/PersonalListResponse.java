package com.avegarlabs.costosservice.dto;

import com.avegarlabs.costosservice.dto.WorkerListItem;
import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonalListResponse {
    private List<WorkerListItem> personal;
}
