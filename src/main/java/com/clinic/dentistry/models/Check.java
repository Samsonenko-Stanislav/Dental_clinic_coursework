package com.clinic.dentistry.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "app_check")
public class Check {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
}

