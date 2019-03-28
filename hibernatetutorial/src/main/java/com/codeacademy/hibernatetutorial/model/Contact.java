package com.codeacademy.hibernatetutorial.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Contact {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "ref_id")
    private Employee employee;


    @Getter
    @Setter
    private String contact_type;

    @Getter
    @Setter
    private String value;

    public Employee getEmployee() {
        return employee;
    }
}
