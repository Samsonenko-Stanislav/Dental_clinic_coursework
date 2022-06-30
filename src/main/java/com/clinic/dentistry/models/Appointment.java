package com.clinic.dentistry.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getDoctor() {
        return doctor;
    }

    public void setDoctor(Employee doctor) {
        this.doctor = doctor;
    }

    public OutpatientCard getClient() {
        return client;
    }

    public void setClient(OutpatientCard client) {
        this.client = client;
    }


    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Appointment() {
    }

    public Appointment(Employee doctor, OutpatientCard client, LocalDateTime date) {
        this.doctor = doctor;
        this.client = client;
        this.date = date;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}
