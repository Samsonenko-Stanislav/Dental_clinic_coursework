package com.clinic.dentistry.service;

import com.clinic.dentistry.dto.AddAppointmentDTO;
import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.AppointmentDto;
import com.clinic.dentistry.dto.AppointmentEditForm;
import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface AppointmentService {

    List<AppointmentDto> getAvailableDatesByDoctors(Iterable<User> doctors);
    Iterable<Appointment> getArchiveAppointmentsForClient(User user);
    Iterable<Appointment> getArchiveAppointmentsForDoctor(User user);
    Iterable<Appointment> getActiveAppointmentsForClient(User user);
    Iterable<Appointment> getActiveAppointmentsForDoctor(User user);
    List<Appointment> getClientList(User user);
    List<Appointment> getDoctorList(User user);

    Iterable<User> getActiveDoctors();
    Appointment addAppointment(User user, AddAppointmentDTO addAppointment);
    void cancelAppointment(Appointment appointment);
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
