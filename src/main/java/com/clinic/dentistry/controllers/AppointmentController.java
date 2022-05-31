package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Role;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.repo.AppointmentRepository;
import com.clinic.dentistry.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/appointments")
    public String appointmentsMain(Model model){
        Iterable<Appointment> appointments = appointmentRepository.findAll();
        model.addAttribute("appointments", appointments);
        return "appointments-main";

    }

    @GetMapping("/appointments/add")
    public String appointmentsAddForm(Model model){
        model.addAttribute("doctors", userRepository.findByRolesIn(Collections.singleton(Role.DOCTOR)));
        return "appointments-add";
    }

    @PostMapping("/appointments/add")
    public String appointmentsAdd(@AuthenticationPrincipal User user,
                                  @RequestParam User doctor, @RequestParam String client,
                                  @RequestParam String dateStr, Model model){
        LocalDateTime date = LocalDateTime.parse(dateStr);
//        Appointment appointment = new Appointment(doctor, user, date);
        Appointment appointment = new Appointment();
        appointmentRepository.save(appointment);
        return "redirect:/appointments";
    }
}
