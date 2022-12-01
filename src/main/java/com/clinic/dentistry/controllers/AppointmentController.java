package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.*;
import com.clinic.dentistry.repo.*;
import com.clinic.dentistry.service.AppointmentService;
import com.clinic.dentistry.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/appointments")
@PreAuthorize("hasAuthority('USER') or hasAuthority('DOCTOR')")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private CheckRepository checkRepository;
    @Autowired
    private CheckLineRepository checkLineRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private CheckService checkService;

    @GetMapping("/")
    public String appointmentsMain(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "withArchived", required = false) String withArchived,
            Model model
    ){
        Iterable<Appointment> appointmentsClient;
        Iterable<Appointment> appointmentsDoctor;
        if (withArchived != null){
            appointmentsClient = appointmentService.getArchiveAppointmentsForClient(user);
            appointmentsDoctor = appointmentService.getArchiveAppointmentsForDoctor(user);
            model.addAttribute("withArchived", true);
        } else {
            appointmentsClient = appointmentService.getActiveAppointmentsForClient(user);
            appointmentsDoctor = appointmentService.getActiveAppointmentsForDoctor(user);
            model.addAttribute("withArchived", false);
        }


        model.addAttribute("appointmentsClient", appointmentsClient);
        model.addAttribute("appointmentsDoctor", appointmentsDoctor);
        return "appointments-main";

    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('USER')")
    public String appointmentsAddForm(Model model){
        Iterable<User> doctors = appointmentService.getActiveDoctors();
        Map<User, Map<String, ArrayList<String>>> availableDatesByDoctor = appointmentService.getAvailableDatesByDoctors(doctors);
        model.addAttribute("doctors", availableDatesByDoctor);
        return "appointments-add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('USER')")
    public String appointmentsAdd(@AuthenticationPrincipal User user, @RequestParam User doctor,
                                  @RequestParam String dateStr, Model model){
        appointmentService.addAppointment(dateStr, doctor, user);
        return "redirect:/appointments/";
    }

    @GetMapping("/{appointment}/edit")
    public String appointmentsEdit(@AuthenticationPrincipal User user,
                                   @PathVariable Appointment appointment,
                                   Model model
    ){
        LocalDateTime now = LocalDateTime.now();
        Boolean readOnly = Boolean.TRUE;
        Boolean canCancel = Boolean.FALSE;
        if (appointment.getDoctor() != null && user.getEmployee() != null &&
                appointment.getDoctor().getId().equals(user.getEmployee().getId())
                && now.isAfter(appointment.getDate())
                && appointment.getActive()
        ){
            readOnly = Boolean.FALSE;
            Iterable<Good> goods = goodRepository.findAllByActiveTrue();
            model.addAttribute("goods", goods);
        }

        Optional<Check> check = checkRepository.findFirstByAppointment(appointment);
        if (check.isPresent()){
            Iterable<CheckLine> checkLines = checkLineRepository.findByCheck(check.get());
            model.addAttribute("checkLines", checkLines);
        }

        if (appointment.getClient() != null && user.getOutpatientCard() != null
                && appointment.getClient().getId().equals(user.getOutpatientCard().getId())
                && now.isBefore(appointment.getDate())
        ){
            canCancel = Boolean.TRUE;
        }

        model.addAttribute("appointment", appointment);
        model.addAttribute("readOnly", readOnly);
        model.addAttribute("canCancel", canCancel);
        return "appointments-edit";
    }

    @PostMapping("/{appointment}/edit")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public String appointmentsEdit(@AuthenticationPrincipal User user,
                                   @PathVariable Appointment appointment,
                                   @RequestParam Map<String, String> form
    ){

        checkService.addConclusion(appointment, form);
        String checkJson = form.get("checkJson");
        checkService.createCheckFromJson(checkJson, appointment);
        return "redirect:/appointments/" + appointment.getId().toString() + "/edit";
    }

    @GetMapping("/{appointment}/cancel")
    @PreAuthorize("hasAuthority('USER')")
    public String appointmentsCancel(@AuthenticationPrincipal User user,
                                     @PathVariable Appointment appointment,
                                     Model model
    ){
        appointment.setActive(Boolean.FALSE);
        appointmentRepository.save(appointment);
        return "redirect:/appointments/";
    }
}
