package com.clinic.dentistry.controllers;

import com.clinic.dentistry.dto.AddAppointmentDTO;
import com.clinic.dentistry.dto.AppointmentDto;
import com.clinic.dentistry.dto.AppointmentEditForm;
import com.clinic.dentistry.models.*;
import com.clinic.dentistry.service.AppointmentService;
import com.clinic.dentistry.service.CheckService;
import com.clinic.dentistry.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
@PreAuthorize("hasAuthority('USER') or hasAuthority('DOCTOR')")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final CheckService checkService;
    private final GoodService goodService;

    @GetMapping("/clientList")
    @PreAuthorize("hasAuthority('USER')")
    public Iterable<Appointment> getClientAppointments(@AuthenticationPrincipal User user) {
        return appointmentService.getClientList(user);
    }

    @GetMapping("/doctorList")
    @PreAuthorize("hasAuthority('DOCTOR')")
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
    public HttpStatus appointmentsAdd(@AuthenticationPrincipal User user, @RequestBody AddAppointmentDTO addAppointment) {
        appointmentService.addAppointment(user, addAppointment);
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
                                       @RequestBody AppointmentEditForm form
    ) {
        Appointment appointment = appointmentService.findAppointment(appointmentId);
        if (appointment != null) {
            checkService.addConclusion(appointment, form);
            checkService.createCheckFromForm(form, appointment);
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