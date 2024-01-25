package com.avegarlabs.hiringservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankAccountModel {
    private String account;
    private String accountNumber;
    private String license;
    private String register;
    private Boolean state;
    private String bankId;
    private String currencyId;
    private String accountOwner;
}
