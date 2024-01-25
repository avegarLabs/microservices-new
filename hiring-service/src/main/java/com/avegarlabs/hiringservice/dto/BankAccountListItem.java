package com.avegarlabs.hiringservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankAccountListItem {
    private String id;
    private String account;
    private String accountNumber;
    private String license;
    private String register;
    private Boolean state;
    private BankListItem bank;
    private CurrencyListItem currency;
    private String accountOwner;
}
