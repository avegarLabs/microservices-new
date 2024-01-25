package com.avegarlabs.hiringservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientModel {
    private String name;
    private String reupCode;
    private String shareCode;
    private String organization;
    private String constResolution;
    private String address;
    private String phone;
    private String email;
    private String fax;
    private String nit;
    private String acronym;
    private String enterpriseGroup;
}
