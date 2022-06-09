package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.models.Role;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.repo.AppointmentRepository;
import com.clinic.dentistry.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/appointments")
    public String appointmentsMain(@AuthenticationPrincipal User user, Model model){
        Iterable<Appointment> appointments = appointmentRepository.findByClientOrDoctorAndActiveTrue(user, user);
        model.addAttribute("appointments", appointments);
        return "appointments-main";

    }

    @GetMapping("/appointments/add")
    public String appointmentsAddForm(Model model){
        Iterable<User> doctors = userRepository.findByRolesIn(Collections.singleton(Role.DOCTOR));
        Iterable<Appointment> appointments = appointmentRepository.findByActiveTrue();

        Map<User, Map<String ,ArrayList<String>>> availableDatesByDoctor = new HashMap<>();
        Map<String ,ArrayList<String>> availableDates;
        ArrayList<String> avalibleTimes;
        Boolean dateTaken;

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

        for (User doctor : doctors) {
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusWeeks(2);
            availableDates = new TreeMap<>();
            for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                avalibleTimes = new ArrayList();
                LocalTime workStart = LocalTime.of(8, 0);
                LocalTime workEnd = LocalTime.of(17, 0);
                if (doctor.getEmployee() != null) {
                    workStart = doctor.getEmployee().getWorkStart();
                    workEnd = doctor.getEmployee().getWorkEnd();
                }
                LocalDateTime startTime = date.atTime(workStart);
                LocalDateTime endTime = date.atTime(workEnd);
                for (LocalDateTime time = startTime; time.isBefore(endTime); time = time.plusMinutes(30)) {
                    dateTaken = Boolean.FALSE;
                    for (Appointment appointment : appointments){
                        if (appointment.getDoctor().equals(doctor) && appointment.getDate().equals(time)) {
                            dateTaken = Boolean.TRUE;
                        }
                    }
                    if (dateTaken.equals(Boolean.FALSE)){
                        avalibleTimes.add(time.format(formatterTime));
                    }
                }
                availableDates.put(date.format(formatterDate), avalibleTimes);
            }
            availableDatesByDoctor.put(doctor, availableDates);
        }

        model.addAttribute("doctors", availableDatesByDoctor);
        return "appointments-add";
    }

    @PostMapping("/appointments/add")
    public String appointmentsAdd(@AuthenticationPrincipal User user, @RequestParam User doctor,
                                  @RequestParam String dateStr, Model model){
        LocalDateTime date = LocalDateTime.parse(dateStr);
        Appointment appointment = new Appointment(doctor, user, date);
        appointment.setActive(Boolean.TRUE);
//        Appointment appointment = new Appointment();
        appointmentRepository.save(appointment);
        return "redirect:/appointments";
    }

    @GetMapping("/appointments/{appointment}/edit")
    public String appointmentsEdit(@AuthenticationPrincipal User user,
                                   @PathVariable Appointment appointment,
                                   Model model
    ){
        Boolean readOnly = Boolean.TRUE;
        if (appointment.getDoctor() != null && appointment.getDoctor().getId() == user.getId()){
            readOnly = Boolean.FALSE;
        }

        model.addAttribute("appointment", appointment);
        model.addAttribute("readOnly", readOnly);
        return "appointments-edit";
    }

    @PostMapping("/appointments/{appointment}/edit")
    public String appointmentsEdit(@AuthenticationPrincipal User user,
                                   @PathVariable Appointment appointment,
                                   @RequestParam Map<String, String> form,
                                   Model model
    ){
        Boolean readOnly = Boolean.TRUE;
        if (appointment.getDoctor() != null && appointment.getDoctor().getId() == user.getId()){
            readOnly = Boolean.FALSE;
            appointment.setConclusion(form.get("conclusion"));
            appointmentRepository.save(appointment);
        }

        model.addAttribute("appointment", appointment);
        model.addAttribute("readOnly", readOnly);
        return "appointments-edit";
    }

    @GetMapping("/appointments/{appointment}/cancel")
    public String appointmentsCancel(@AuthenticationPrincipal User user,
                                     @PathVariable Appointment appointment,
                                     Model model
    ){
        appointment.setActive(Boolean.FALSE);
        appointmentRepository.save(appointment);
        return "redirect:/appointments";
    }
}
