package com.avegarlabs.hiringservice.dto;

import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChargesListResponse {
    private List<ChargesListItem> cargos;
}
