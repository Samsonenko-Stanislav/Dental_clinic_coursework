package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.repo.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/appointments")
    public String appointmentsMain(Model model){
        Iterable<Appointment> appointments = appointmentRepository.findAll();
        model.addAttribute("appointments", appointments);
        return "appointments-main";

    }

    @GetMapping("/appointments/add")
    public String appointmentsAddForm(Model model){
        return "appointments-add";
    }

    @PostMapping("/appointments/add")
    public String appointmentsAdd(@AuthenticationPrincipal User user,
                                  @RequestParam String doctor, @RequestParam String client,
                                  @RequestParam String dateStr, Model model){
        LocalDateTime date = LocalDateTime.parse(dateStr);
        Appointment appointment = new Appointment(doctor, user, date);
        appointmentRepository.save(appointment);
        return "redirect:/appointments";
    }
}
