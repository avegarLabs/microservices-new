package com.avegarlabs.hiringservice.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Enterprise {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;
    private String name;
    private String reupCode;
    private String organization;
    private String address;
    private String phone;
    private String email;
    private String fax;
    private String nit;
    private String acronym;
    private String enterpriseGroup;

    private String moniker;

    public String creationDate;
    @Temporal(TemporalType.DATE)
    private Date lastUpdateTime;
}
