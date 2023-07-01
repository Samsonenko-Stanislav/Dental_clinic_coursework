package com.clinic.dentistry.service;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.user.UserEditForm;
import com.clinic.dentistry.models.User;

public interface OutpatientCardService {

    ApiResponse userMeEdit(User user, UserEditForm updateUser);

}
