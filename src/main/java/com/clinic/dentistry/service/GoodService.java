package com.clinic.dentistry.service;

import com.clinic.dentistry.models.Good;

public interface GoodService {
    Iterable<Good> findActiveGoods();
}
