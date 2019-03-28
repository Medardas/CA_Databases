package com.codeacademy.hibernatetutorial.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "employee")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String street;
    @Getter
    @Setter
    private String city;

    @Setter
    @OneToOne(mappedBy = "address")
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }
}
