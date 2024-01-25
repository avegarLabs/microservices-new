package com.avegarlabs.hiringservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CurrencyListItem {
        private String id;
        private String name;
        private String symbol;
        private Boolean state;
}
