package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.models.Good;
import com.clinic.dentistry.repo.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class GoodServiceImpl {
    @Autowired
    private GoodRepository goodRepository;

    public Iterable<Good> findActiveGoods(){
      return goodRepository.findAllByActiveTrue();
   }
}
