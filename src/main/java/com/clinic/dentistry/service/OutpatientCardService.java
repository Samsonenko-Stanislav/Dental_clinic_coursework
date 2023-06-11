package com.clinic.dentistry.service;

import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.models.User;

import java.util.List;
import java.util.Map;

public interface OutpatientCardService {

    List<OutpatientCard> findAllCards();
    void userMeEdit(User user, User updateUser, Boolean changePassword);

}
