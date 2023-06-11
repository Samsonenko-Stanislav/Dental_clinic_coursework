package com.clinic.dentistry.service;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Check;
import com.clinic.dentistry.models.CheckLine;

import java.util.Map;
import java.util.Optional;

public interface CheckService {

    Check createCheckFromJson(Map<String, String> form, Appointment appointment);
    void addConclusion(Appointment appointment, Map<String, String> form);
    Optional<Check> findCheck(Appointment appointment);

    public Iterable<CheckLine> findCheckLines(Optional<Check> check);

}
