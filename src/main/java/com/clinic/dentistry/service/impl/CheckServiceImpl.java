package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.dto.AppointmentEditForm;
import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Check;
import com.clinic.dentistry.models.CheckLine;
import com.clinic.dentistry.models.Good;
import com.clinic.dentistry.repo.AppointmentRepository;
import com.clinic.dentistry.repo.CheckLineRepository;
import com.clinic.dentistry.repo.CheckRepository;
import com.clinic.dentistry.repo.GoodRepository;
import com.clinic.dentistry.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CheckServiceImpl implements CheckService {

    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private CheckRepository checkRepository;
    @Autowired
    private CheckLineRepository checkLineRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Check createCheckFromForm(AppointmentEditForm form, Appointment appointment) {
        Check check;
        Optional<Good> good;
        CheckLine checkLine;
        check = new Check();
        check.setAppointment(appointment);
        checkRepository.save(check);
        for (AppointmentEditForm.Check formCheck : form.getChecks()) {
            good = goodRepository.findById(formCheck.getGoodId());
            if (good.isPresent()) {
                checkLine = new CheckLine();
                checkLine.setGood(good.get());
                checkLine.setPrice(formCheck.getPrice());
                checkLine.setQty(formCheck.getQty());
                checkLine.setCheck(check);
                checkLineRepository.save(checkLine);
            }
        }

        return check;
    }

    @Override
    public void addConclusion(Appointment appointment, AppointmentEditForm form) {
        appointment.setConclusion(form.getConclusion());
        appointment.setActive(false);
        appointmentRepository.save(appointment);
    }

    @Override
    public Optional<Check> findCheck(Appointment appointment) {
        return checkRepository.findFirstByAppointment(appointment);
    }

    @Override
    public Iterable<CheckLine> findCheckLines(Optional<Check> check) {
        return checkLineRepository.findByCheck(check.get());
    }

}
