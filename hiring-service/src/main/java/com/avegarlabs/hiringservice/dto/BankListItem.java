package com.avegarlabs.hiringservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankListItem {
    private String id;
    private String name;
    private String code;
    private String denomination;
    private Boolean state;

}
