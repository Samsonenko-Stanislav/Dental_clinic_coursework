package com.clinic.dentistry.models;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private OutpatientCard client;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Employee doctor;
    private LocalDateTime date;
    private Boolean active;

    @Type(type="text")
    private String conclusion;

    public String getClientName(){
        return client != null ? client.getFullName() : "None";
    }

    public String getDoctorName(){
        return doctor != null ? doctor.getFullName() : "None";
    }
    public String getDoctorJobTitle(){
        return doctor != null ? doctor.getJobTitle() : "None";
    }
}
