package com.clinic.dentistry.service;

import com.clinic.dentistry.models.Good;

import java.util.Map;

public interface GoodService {
    Iterable<Good> findActiveGoods();
    Iterable<Good> findAllGoods();
    void goodSave(Good good);
    void goodEdit(Long i, Good newData);
    Good findGood(Long id);
}
