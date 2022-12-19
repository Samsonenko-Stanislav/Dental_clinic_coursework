package com.clinic.dentistry.service;

import com.clinic.dentistry.models.Good;

import java.util.Map;

public interface GoodService {
    Iterable<Good> findActiveGoods();
    Iterable<Good> findAllGoods();
    void goodSave(Good good);
    void goodEdit(Good good, Good good_new, Map<String, String> form);
    Good findGood(Long id);
}
