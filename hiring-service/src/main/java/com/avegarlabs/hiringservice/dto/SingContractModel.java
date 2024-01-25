package com.avegarlabs.hiringservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SingContractModel {
    private String name;
    private String lastName;
    private String charge;
    private String clientId;
}

