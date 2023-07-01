package com.clinic.dentistry.controllers;

import com.clinic.dentistry.dto.AddAppointmentDTO;
import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.AppointmentDto;
import com.clinic.dentistry.dto.AppointmentEditForm;
import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
@PreAuthorize("hasAuthority('USER') or hasAuthority('DOCTOR')")
public class AppointmentController {
    private final AppointmentService appointmentService;

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
        List<AppointmentDto> availableDatesByDoctor = appointmentService.appointmentsAddForm();
        return availableDatesByDoctor;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> appointmentsAdd(@AuthenticationPrincipal User user, @RequestBody AddAppointmentDTO addAppointment) {
        ApiResponse response = appointmentService.appointmentsAdd(user,addAppointment);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/edit/{appointmentId}")
    public Map<String, Object> appointmentsEdit(@AuthenticationPrincipal User user,
                                                @PathVariable("appointmentId") Long appointmentId
    ) {
        return appointmentService.getAppointment(user, appointmentId);
    }

    @PutMapping("/edit/{appointmentId}")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<?> appointmentsEdit(@AuthenticationPrincipal User user,
                                       @PathVariable("appointmentId") Long appointmentId,
                                       @RequestBody AppointmentEditForm form
    ) {

        ApiResponse response = appointmentService.editAppointment(user, appointmentId, form);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/cancel/{appointmentId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> appointmentsCancel(@AuthenticationPrincipal User user,
                                         @PathVariable("appointmentId") Long appointmentId
    ) {
        ApiResponse response = appointmentService.appointmentsCancel(user, appointmentId);
        return new ResponseEntity<>(response, response.getStatus());
    }
}