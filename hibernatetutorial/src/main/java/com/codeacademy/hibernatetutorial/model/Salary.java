package com.codeacademy.hibernatetutorial.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
public class Salary {
    @Getter
    @Id
    private int personId;

    @Setter
    @Getter
    private int pay;

    @Setter
    @OneToOne
    @MapsId
    // pasako hibernate naudoti Salary "personId" identifikaciją kaip primary key ir foreign key nurodytam Entity.
    private Person person;

    public Person getPerson() {
        return person;
    }

}
