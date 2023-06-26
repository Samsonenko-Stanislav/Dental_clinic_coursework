package com.clinic.dentistry.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name="good_id", initialValue=5)
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "good_id")
    private Long id;

    private boolean active= true;

    private String name;

    private float price;

}
