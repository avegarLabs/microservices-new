package com.avegarlabs.hiringservice.models;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Currency {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;
    private String name;
    private String symbol;
    private Boolean state;

    @OneToMany(mappedBy="currency")
    private Set<BankAccount> bankAccounts;

}
