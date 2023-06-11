package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Check;
import com.clinic.dentistry.models.CheckLine;
import com.clinic.dentistry.models.Good;
import com.clinic.dentistry.repo.AppointmentRepository;
import com.clinic.dentistry.repo.CheckLineRepository;
import com.clinic.dentistry.repo.CheckRepository;
import com.clinic.dentistry.repo.GoodRepository;
import com.clinic.dentistry.service.CheckService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hibernate.Session;
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
    public Check createCheckFromJson(Map<String, String> form, Appointment appointment) {
        Check check;
        String checkJson = form.get("checkJson");
        if (checkJson.equals("")){
            return null;
        }

        JsonArray goodsJson = new Gson().fromJson(checkJson, JsonArray.class);

        Float goodPrice;
        int goodQty;
        long good_id;
        Optional<Good> good;
        CheckLine checkLine;
        check = new Check();
        check.setAppointment(appointment);
        checkRepository.save(check);
        for (JsonElement goodJson : goodsJson) {
            goodPrice = ((JsonObject) goodJson).get("price").getAsFloat();
            good_id = ((JsonObject) goodJson).get("good_id").getAsLong();
            goodQty = ((JsonObject) goodJson).get("qty").getAsInt();
            good = goodRepository.findById(good_id);
            if (good.isPresent()){
                checkLine = new CheckLine();
                checkLine.setGood(good.get());
                checkLine.setPrice(goodPrice);
                checkLine.setQty(goodQty);
                checkLine.setCheck(check);
                checkLineRepository.save(checkLine);
            }
        }

        return check;
    }

    @Override
    public void addConclusion(Appointment appointment, Map<String, String> form){
        appointment.setConclusion(form.get("conclusion"));
        appointment.setActive(false);
        appointmentRepository.save(appointment);
    }

    @Override
    public Optional<Check> findCheck(Appointment appointment){
        return checkRepository.findFirstByAppointment(appointment);
    }

    @Override
    public Iterable<CheckLine> findCheckLines(Optional<Check> check){
        return checkLineRepository.findByCheck(check.get());
    }

}
