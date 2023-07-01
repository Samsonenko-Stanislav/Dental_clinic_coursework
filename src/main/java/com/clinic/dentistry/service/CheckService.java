package com.clinic.dentistry.service;

import com.clinic.dentistry.dto.AppointmentEditForm;
import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Check;
import com.clinic.dentistry.models.CheckLine;

import java.util.Optional;

public interface CheckService {

    void createCheckFromForm(AppointmentEditForm form, Appointment appointment);
    void addConclusion(Appointment appointment, AppointmentEditForm form);
    Optional<Check> findCheck(Appointment appointment);

    Iterable<CheckLine> findCheckLines(Optional<Check> check);

}
