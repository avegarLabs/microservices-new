package com.avegarlabs.coninsuservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FormaterListItem {
    private String id;
    private String type;
    private BigDecimal price;
}
