package com.codeacademy.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@RequiredArgsConstructor
public class Salary {

    @Id
    @Column(name = "person_id")
    private Long id;

    @NonNull
    private int pay;

    private Salary() {
    }
}
