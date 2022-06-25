package com.clinic.dentistry.service;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Check;

public interface CheckService {

    Check createCheckFromJson(String checkJson, Appointment appointment);

}
