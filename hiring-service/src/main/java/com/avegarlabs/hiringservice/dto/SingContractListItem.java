package com.avegarlabs.hiringservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SingContractListItem {
    private String id;
    private String name;
    private String lastName;
    private String charge;
    private ClientListItem clientListItem;
}
