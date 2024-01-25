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
public class Contracts {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;
    private String name;
    private String number;
    private String description;
    private String about;
    private String state;
    public String creationDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Temporal(TemporalType.DATE)
    private Date aproveDate;
    private String moniker;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_request_id")
    private ServiceRequest serviceRequest;
}
