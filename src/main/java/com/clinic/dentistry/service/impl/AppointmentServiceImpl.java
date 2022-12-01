package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Role;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.repo.AppointmentRepository;
import com.clinic.dentistry.repo.UserRepository;
import com.clinic.dentistry.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<User, Map<String, ArrayList<String>>> getAvailableDatesByDoctors(Iterable<User> doctors) {
        Map<User, Map<String ,ArrayList<String>>> availableDatesByDoctor = new HashMap<>();
        Map<String ,ArrayList<String>> availableDates;
        ArrayList<String> avalibleTimes;
        Boolean dateTaken;

        Iterable<Appointment> appointments = appointmentRepository.findByActiveTrue();

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

        for (User doctor : doctors) {
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusWeeks(2);
            availableDates = new TreeMap<>();
            for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
               if (!(date.getDayOfWeek().equals(DayOfWeek.SUNDAY) || (date.getDayOfWeek().equals(DayOfWeek.MONDAY)))) {
                    avalibleTimes = new ArrayList();
                    LocalTime workStart = LocalTime.of(8, 0);
                    LocalTime workEnd = LocalTime.of(17, 0);
                    if (doctor.getEmployee() != null) {
                        workStart = doctor.getEmployee().getWorkStart();
                        workEnd = doctor.getEmployee().getWorkEnd();
                    }
                    LocalDateTime startTime = date.atTime(workStart);
                    LocalDateTime endTime = date.atTime(workEnd);
                    for (LocalDateTime time = startTime; time.isBefore(endTime); time = time.plusMinutes(doctor.getEmployee().getDurationApp())) {
                        dateTaken = Boolean.FALSE;
                        for (Appointment appointment : appointments) {
                            if (appointment.getDoctor().equals(doctor.getEmployee()) && appointment.getDate().equals(time)) {
                                dateTaken = Boolean.TRUE;
                            }
                        }
                        if (dateTaken.equals(Boolean.FALSE)) {
                            avalibleTimes.add(time.format(formatterTime));
                        }
                    }
                    availableDates.put(date.format(formatterDate), avalibleTimes);
                }
            }
            availableDatesByDoctor.put(doctor, availableDates);
        }

        return availableDatesByDoctor;
    }
    @Override
    public Iterable<Appointment> getArchiveAppointmentsForClient(User user){
        return appointmentRepository.findByClientAndConclusionNotNull(user.getOutpatientCard());
    }

    @Override
    public Iterable<Appointment> getArchiveAppointmentsForDoctor(User user){
        return appointmentRepository.findByDoctorAndConclusionNotNull(user.getEmployee());
    }

    @Override
    public Iterable<Appointment> getActiveAppointmentsForClient(User user){
        return appointmentRepository.findByClientAndActiveTrueAndConclusionNull(user.getOutpatientCard());
    }

    @Override
    public Iterable<Appointment> getActiveAppointmentsForDoctor(User user){
        return appointmentRepository.findByDoctorAndActiveTrueAndConclusionNull(user.getEmployee());
    }

    @Override
    public Iterable<User> getActiveDoctors(){
        return userRepository.findByRolesInAndActiveTrue(Collections.singleton(Role.DOCTOR));
    }

    @Override
    public Appointment addAppointment(String dateStr, User doctor, User user){
        LocalDateTime date = LocalDateTime.parse(dateStr);
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor.getEmployee());
        appointment.setClient(user.getOutpatientCard());
        appointment.setDate(date);
        appointment.setActive(Boolean.TRUE);
        appointmentRepository.save(appointment);
        return appointment;
    }

}
