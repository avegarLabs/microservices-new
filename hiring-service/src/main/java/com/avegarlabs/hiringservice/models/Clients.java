package com.avegarlabs.hiringservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Clients {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;
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

    private String moniker;

    public String creationDate;
    @Temporal(TemporalType.DATE)
    private Date lastUpdateTime;


    @OneToMany(mappedBy="client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ServiceRequest> serviceRequestSet;

    @OneToMany(mappedBy="client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<SingContract> singContracts;

}
