package com.clinic.dentistry.service;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Check;

import java.util.Map;

public interface CheckService {

    Check createCheckFromJson(String checkJson, Appointment appointment);
    void addConclusion(Appointment appointment, Map<String, String> form);

}
