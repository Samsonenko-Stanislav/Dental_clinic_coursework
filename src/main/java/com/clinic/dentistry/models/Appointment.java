package com.clinic.dentistry.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private User client;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private User doctor;
    private LocalDateTime date;
    private Boolean active;

    @Type(type="text")
    private String conclusion;

    public String getClientName(){
        return client != null ? client.getUsername() : "None";
    }
    public String getDoctorName(){
        return doctor != null ? doctor.getFullName() : "None";
    }
    public String getDoctorJobTitle(){
        return doctor != null ? doctor.getEmployeeJobTitle() : "None";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Appointment(User doctor, User client, LocalDateTime date) {
        this.doctor = doctor;
        this.client = client;
        this.date = date;
    }
    public Appointment(User doctor, LocalDateTime date) {
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
