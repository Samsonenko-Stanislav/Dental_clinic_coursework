package com.clinic.dentistry.service;

import com.clinic.dentistry.models.User;

import java.util.ArrayList;
import java.util.Map;

public interface AppointmentService {

    Map<User, Map<String , ArrayList<String>>> getAvailableDatesByDoctors(Iterable<User> doctors);

}
