package com.clinic.dentistry.service;

import com.clinic.dentistry.dto.AddAppointmentDTO;
import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.AppointmentDto;
import com.clinic.dentistry.dto.AppointmentEditForm;
import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.User;

import java.util.List;
import java.util.Map;

public interface AppointmentService {

    List<AppointmentDto> getAvailableDatesByDoctors(Iterable<User> doctors);


    List<Appointment> getClientList(User user);
    List<Appointment> getDoctorList(User user);

    Iterable<User> getActiveDoctors();
    void addAppointment(User user, AddAppointmentDTO addAppointment);

    Boolean isCanEditByDoctor(User user, Appointment appointment);
    Boolean isCanCancel(User user, Appointment appointment);
    Appointment findAppointment(Long id);
    Boolean isVacantAppointment(AddAppointmentDTO addAppointment);
    Boolean isUserAppointment(User user, Appointment appointment);
    List<AppointmentDto> appointmentsAddForm();
    ApiResponse appointmentsAdd(User user, AddAppointmentDTO addAppointment);

    Map<String, Object> getAppointment(User user, Long appointmentId);

    ApiResponse editAppointment(User user, Long appointmentId, AppointmentEditForm form);

    ApiResponse appointmentsCancel(User user, Long appointmentId);
}
