package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.*;
import com.clinic.dentistry.repo.*;
import com.clinic.dentistry.service.AppointmentService;
import com.clinic.dentistry.service.CheckService;
import com.clinic.dentistry.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/appointments")
@PreAuthorize("hasAuthority('USER') or hasAuthority('DOCTOR')")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private CheckService checkService;
    @Autowired
    private GoodService goodService;

    @GetMapping()
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
    public String appointmentsAdd(@AuthenticationPrincipal User user, @RequestParam("doctorId") User doctor,
                                  @RequestParam("dateStr") String dateStr, Model model){
        appointmentService.addAppointment(dateStr, doctor, user);
        return "redirect:/appointments/";
    }

    @GetMapping("/{appointmentId}/edit")
    public String appointmentsEdit(@AuthenticationPrincipal User user,
                                   @PathVariable("appointmentId") Long appointmentId,
                                   Model model
    ){
        Appointment appointment = appointmentService.findAppointment(appointmentId);
        if (appointment != null) {
            Boolean readOnly = Boolean.TRUE;
            Boolean canCancel = Boolean.FALSE;
            if (appointmentService.isCanEditByDoctor(user, appointment)) {
                readOnly = Boolean.FALSE;
                Iterable<Good> goods = goodService.findActiveGoods();
                model.addAttribute("goods", goods);
            }

            Optional<Check> check = checkService.findCheck(appointment);
            if (check.isPresent()) {
                Iterable<CheckLine> checkLines = checkService.findCheckLines(check);
                model.addAttribute("checkLines", checkLines);
            }

            if (appointmentService.isCanCancel(user, appointment)) {
                canCancel = Boolean.TRUE;
            }

            model.addAttribute("appointment", appointment);
            model.addAttribute("readOnly", readOnly);
            model.addAttribute("canCancel", canCancel);
            return "appointments-edit";
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/{appointmentId}/edit")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public String appointmentsEdit(@AuthenticationPrincipal User user,
                                   @PathVariable("appointmentId") Long appointmentId,
                                   @RequestParam Map<String, String> form
    ){
        Appointment appointment = appointmentService.findAppointment(appointmentId);
        if (appointment != null){
        checkService.addConclusion(appointment, form);
        checkService.createCheckFromJson(form, appointment);
        return "redirect:/appointments/" + appointment.getId().toString() + "/edit";
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/{appointmentId}/cancel")
    @PreAuthorize("hasAuthority('USER')")
    public String appointmentsCancel(@AuthenticationPrincipal User user,
                                     @PathVariable("appointmentId") Appointment appointment
    ){
        if (appointmentService.isCanCancel(user, appointment)){
            appointmentService.cancelAppointment(appointment);
            return "redirect:/appointments/";
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST
        );
    }
}
