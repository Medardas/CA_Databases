package com.codeacademy.hibernatetutorial.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "ref_id")
    private Employee employee;

    private String contact_type;
    private String value;

}
