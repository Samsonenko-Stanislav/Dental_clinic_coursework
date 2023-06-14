package com.clinic.dentistry.controllers;

import com.clinic.dentistry.dto.AppointmentDto;
import com.clinic.dentistry.models.*;
import com.clinic.dentistry.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
@PreAuthorize("hasAuthority('USER') or hasAuthority('DOCTOR')")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final CheckService checkService;
    private final GoodService goodService;

    @GetMapping("/clientList")
    public Iterable<Appointment> getClientAppointments(@AuthenticationPrincipal User user) {
        return appointmentService.getClientList(user);
    }

    @GetMapping("/doctorList")
    public Iterable<Appointment> getDoctorAppointments(@AuthenticationPrincipal User user) {
        return appointmentService.getDoctorList(user);
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('USER')")
    public List<AppointmentDto> appointmentsAddForm() {
        Map<String, Object> model = new HashMap<>();

        Iterable<User> doctors = appointmentService.getActiveDoctors();
        List<AppointmentDto> availableDatesByDoctor = appointmentService.getAvailableDatesByDoctors(doctors);
        model.put("doctors", availableDatesByDoctor);
        return availableDatesByDoctor;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('USER')")
    public HttpStatus appointmentsAdd(@AuthenticationPrincipal User user, @RequestParam("doctorId") User doctor,
                                      @RequestParam("dateStr") String dateStr) {
        appointmentService.addAppointment(dateStr, doctor, user);
        return HttpStatus.CREATED;
    }

    @GetMapping("/{appointmentId}/edit")
    public Map<String, Object> appointmentsEdit(@AuthenticationPrincipal User user,
                                                @PathVariable("appointmentId") Long appointmentId
    ) {
        Map<String, Object> model = new HashMap<>();

        Appointment appointment = appointmentService.findAppointment(appointmentId);
        if (appointment != null) {
            Boolean readOnly = Boolean.TRUE;
            Boolean canCancel = Boolean.FALSE;
            if (appointmentService.isCanEditByDoctor(user, appointment)) {
                readOnly = Boolean.FALSE;
                Iterable<Good> goods = goodService.findActiveGoods();
                model.put("goods", goods);
            }

            Optional<Check> check = checkService.findCheck(appointment);
            if (check.isPresent()) {
                Iterable<CheckLine> checkLines = checkService.findCheckLines(check);
                model.put("checkLines", checkLines);
            }

            if (appointmentService.isCanCancel(user, appointment)) {
                canCancel = Boolean.TRUE;
            }

            model.put("appointment", appointment);
            model.put("readOnly", readOnly);
            model.put("canCancel", canCancel);
            return model;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/{appointmentId}/edit")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public HttpStatus appointmentsEdit(@PathVariable("appointmentId") Long appointmentId,
                                       @RequestParam Map<String, String> form
    ) {
        Appointment appointment = appointmentService.findAppointment(appointmentId);
        if (appointment != null) {
            checkService.addConclusion(appointment, form);
            checkService.createCheckFromJson(form, appointment);
            return HttpStatus.OK;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/{appointmentId}/cancel")
    @PreAuthorize("hasAuthority('USER')")
    public HttpStatus appointmentsCancel(@AuthenticationPrincipal User user,
                                         @PathVariable("appointmentId") Appointment appointment
    ) {
        if (appointmentService.isCanCancel(user, appointment)) {
            appointmentService.cancelAppointment(appointment);
            return HttpStatus.OK;
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST
        );
    }
}