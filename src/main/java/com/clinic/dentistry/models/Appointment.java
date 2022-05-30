package com.clinic.dentistry.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String doctor, status, description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private User client;
    private LocalDateTime date;

    public String getClientName(){
        return client != null ? client.getUsername() : "None";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Appointment() {
    }

    public Appointment(String doctor, User client, LocalDateTime date) {
        this.doctor = doctor;
        this.client = client;
        this.date = date;
    }
    public Appointment(String doctor, LocalDateTime date) {
        this.doctor = doctor;
        this.client = client;
        this.date = date;
    }


}
