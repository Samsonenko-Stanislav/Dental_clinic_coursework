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
import com.clinic.dentistry.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
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

    @Autowired
    private MailService mailService;

    @Override
    public void createCheckFromForm(AppointmentEditForm form, Appointment appointment) {
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

    }

    @Override
    public void addConclusion(Appointment appointment, AppointmentEditForm form) {
        appointment.setConclusion(form.getConclusion());
        appointment.setActive(false);
        appointmentRepository.save(appointment);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm");
        try{
            mailService.sendNotification(
                    "Здравствуйте, " + appointment.getClient().getFullName() + "! \n" +
                            "Заключение врача " + appointment.getDoctor().getJobTitle() +
                            " " + appointment.getDoctor().getFullName() + "  от " + appointment.getDate().format(formatter) +
                            " : \n" + appointment.getConclusion() + "\n" +
                            "\nС заключением, а также со списком оказанных Вам услуг можно ознакомиться по ссылке: \n"
                            + "http://стоматология.online/appointments/" + "edit/" + appointment.getId() +
                            " \n\nС уважением, \n" +
                            "Коллектив стоматологической клиники 'Улыбка премиум' ",
                    appointment.getClient().getEmail(),
                    "Врачебное заключение"
            );
        }catch (MailException ignored) {
        }

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
