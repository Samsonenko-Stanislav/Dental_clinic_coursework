package com.clinic.dentistry.service;

import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.models.User;

import java.util.List;
import java.util.Map;

public interface OutpatientCardService {

    List<OutpatientCard> findAllCards();
    void userMeEdit(User user, Map<String, String> form);

}
