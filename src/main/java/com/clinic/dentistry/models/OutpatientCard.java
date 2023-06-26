package com.clinic.dentistry.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@SequenceGenerator(name="patient_id", initialValue=2)
public class OutpatientCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "patient_id")
    private Long id;

    private String fullName;
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
