package com.codeacademy.hibernatetutorial.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Salary {
    @Id
    private int personId;

    private int pay;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "personId", referencedColumnName = "id")
    private Person person;
}
