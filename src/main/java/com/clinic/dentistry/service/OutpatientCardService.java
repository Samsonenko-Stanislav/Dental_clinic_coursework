package com.clinic.dentistry.service;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.user.UserEditForm;
import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.models.User;

import java.util.List;
import java.util.Map;

public interface OutpatientCardService {

    List<OutpatientCard> findAllCards();
    ApiResponse userMeEdit(User user, UserEditForm updateUser);

}
