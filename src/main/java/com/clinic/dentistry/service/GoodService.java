package com.clinic.dentistry.service;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.models.Good;

import java.util.HashMap;

public interface GoodService {
    Iterable<Good> findActiveGoods();
    Iterable<Good> findAllGoods();
    ApiResponse goodSave(Good good);
    ApiResponse goodEdit(Long i, Good newData);
    Good findGood(Long id);

    HashMap<String, Object> getGoodList();

    HashMap<String, Object> goodGet(Long goodId);

    HashMap<String, Object> priceGet();
}
