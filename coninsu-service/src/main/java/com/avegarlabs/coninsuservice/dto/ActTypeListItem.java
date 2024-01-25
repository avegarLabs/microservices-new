package com.avegarlabs.coninsuservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActTypeListItem {
    private int id;
    private String code;
    private String name;
}
