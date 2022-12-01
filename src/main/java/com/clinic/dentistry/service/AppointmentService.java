package com.clinic.dentistry.service;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.User;
import java.util.ArrayList;
import java.util.Map;

public interface AppointmentService {

    Map<User, Map<String , ArrayList<String>>> getAvailableDatesByDoctors(Iterable<User> doctors);
    Iterable<Appointment> getArchiveAppointmentsForClient(User user);
    Iterable<Appointment> getArchiveAppointmentsForDoctor(User user);
    Iterable<Appointment> getActiveAppointmentsForClient(User user);
    Iterable<Appointment> getActiveAppointmentsForDoctor(User user);
    Iterable<User> getActiveDoctors();
    Appointment addAppointment(String dateStr, User doctor, User user);
    void cancelAppointment(Appointment appointment);
    Boolean isCanEditByDoctor(User user, Appointment appointment);
    Boolean isCanCancel(User user, Appointment appointment);
}
