package com.codeacademy.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String position;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "address")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;

    @Transient
    @OneToMany(mappedBy = "employee")
    private Set<Contact> contacts;
}
