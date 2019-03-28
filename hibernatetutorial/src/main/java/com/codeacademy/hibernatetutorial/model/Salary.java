package com.codeacademy.hibernatetutorial.model;

import lombok.*;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class Salary {
    @Getter
    @Id
    private int personId;

    @Setter
    @Getter
    @NonNull
    private int pay;

    @Setter
// pasako hibernate naudoti Salary "personId" identifikaciją kaip primary key ir foreign key nurodytam Entity.
    @OneToOne
    @JoinColumn(name = "person_id")
    @MapsId
    private Person person;

    public Person getPerson() {
        return person;
    }

}
