package com.codeacademy.hibernatetutorial.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Branch {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    @Getter
    @Setter
    private String name;
    @ManyToOne
    @Setter
    @JoinColumn(name = "company")
    private Company company;

    public Company getCompany() {
        return company;
    }
}
