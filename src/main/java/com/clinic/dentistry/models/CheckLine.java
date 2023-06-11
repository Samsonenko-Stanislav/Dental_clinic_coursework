package com.clinic.dentistry.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "app_check_line")
public class CheckLine{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "check_id")
    private Check check;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "good_id")
    private Good good;

    private int qty;

    private float price;
}
