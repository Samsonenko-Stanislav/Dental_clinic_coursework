package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Check;
import com.clinic.dentistry.models.CheckLine;
import com.clinic.dentistry.models.Good;
import com.clinic.dentistry.repo.CheckLineRepository;
import com.clinic.dentistry.repo.CheckRepository;
import com.clinic.dentistry.repo.GoodRepository;
import com.clinic.dentistry.service.CheckService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CheckServiceImpl implements CheckService {

    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private CheckRepository checkRepository;
    @Autowired
    private CheckLineRepository checkLineRepository;

    @Override
    public Check createCheckFromJson(String checkJson, Appointment appointment) {
        Check check = null;

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
}
