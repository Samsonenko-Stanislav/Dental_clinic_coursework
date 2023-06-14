package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.dto.AppointmentDto;
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

    private LocalDateTime now = LocalDateTime.now();

    @Override
    public List<AppointmentDto> getAvailableDatesByDoctors(Iterable<User> doctors) {
        Map<User, Map<String, ArrayList<String>>> availableDatesByDoctor = new HashMap<>();
        Map<String, ArrayList<String>> availableDates;
        ArrayList<String> avalibleTimes;
        Boolean dateTaken;

        Iterable<Appointment> appointments = appointmentRepository.findByActiveTrue();

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");


        List<AppointmentDto> dtoList = new ArrayList<>();
        for (User doctor : doctors) {
            AppointmentDto dto = new AppointmentDto(doctor);

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

                    dto.timetable.add(
                            new AppointmentDto.DayTimes(date.format(formatterDate), avalibleTimes));
                }
            }
            availableDatesByDoctor.put(doctor, availableDates);

            dtoList.add(dto);
        }

        return dtoList;

//        return availableDatesByDoctor;
    }

    @Override
    public Iterable<Appointment> getArchiveAppointmentsForClient(User user) {
        return appointmentRepository.findByClientAndConclusionNotNull(user.getOutpatientCard());
    }

    @Override
    public Iterable<Appointment> getArchiveAppointmentsForDoctor(User user) {
        return appointmentRepository.findByDoctorAndConclusionNotNull(user.getEmployee());
    }

    @Override
    public Iterable<Appointment> getActiveAppointmentsForClient(User user) {
        return appointmentRepository.findByClientAndActiveTrueAndConclusionNull(user.getOutpatientCard());
    }

    @Override
    public Iterable<Appointment> getActiveAppointmentsForDoctor(User user) {
        return appointmentRepository.findByDoctorAndActiveTrueAndConclusionNull(user.getEmployee());
    }


    @Override
    public List<Appointment> getClientList(User user) {
        List<Appointment> result = new ArrayList<>();
        appointmentRepository.findByClientAndConclusionNotNull(user.getOutpatientCard()).forEach(result::add);
        appointmentRepository.findByClientAndActiveTrueAndConclusionNull(user.getOutpatientCard()).forEach(result::add);
        return result;
    }

    @Override
    public List<Appointment> getDoctorList(User user) {
        List<Appointment> result = new ArrayList<>();
        appointmentRepository.findByDoctorAndConclusionNotNull(user.getEmployee()).forEach(result::add);
        appointmentRepository.findByDoctorAndActiveTrueAndConclusionNull(user.getEmployee()).forEach(result::add);
        return result;
    }


    @Override
    public Iterable<User> getActiveDoctors() {
        return userRepository.findByRolesInAndActiveTrue(Collections.singleton(Role.DOCTOR));
    }

    @Override
    public Appointment addAppointment(String dateStr, User doctor, User user) {
        LocalDateTime date = LocalDateTime.parse(dateStr);
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor.getEmployee());
        appointment.setClient(user.getOutpatientCard());
        appointment.setDate(date);
        appointment.setActive(Boolean.TRUE);
        appointmentRepository.save(appointment);
        return appointment;
    }

    @Override
    public void cancelAppointment(Appointment appointment) {
        appointment.setActive(Boolean.FALSE);
        appointmentRepository.save(appointment);
    }

    @Override
    public Boolean isCanEditByDoctor(User user, Appointment appointment) {
        return appointment.getDoctor() != null && user.getEmployee() != null &&
                appointment.getDoctor().getId().equals(user.getEmployee().getId())
                && now.isAfter(appointment.getDate().toLocalDate().atStartOfDay())
                && appointment.getActive();
    }

    @Override
    public Boolean isCanCancel(User user, Appointment appointment) {
        return appointment.getClient() != null && user.getOutpatientCard() != null
                && appointment.getClient().getId().equals(user.getOutpatientCard().getId())
                && now.isBefore(appointment.getDate().toLocalDate().atStartOfDay().plusDays(1));
    }

    @Override
    public Appointment findAppointment(Long id) {
        return appointmentRepository.findAppointmentById(id);
    }

}
