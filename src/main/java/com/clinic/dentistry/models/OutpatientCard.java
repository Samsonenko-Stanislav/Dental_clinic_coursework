package com.clinic.dentistry.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OutpatientCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;


}
