package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.models.Good;
import com.clinic.dentistry.repo.GoodRepository;
import com.clinic.dentistry.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodServiceImpl implements GoodService {
    @Autowired
    private GoodRepository goodRepository;

    public Iterable<Good> findActiveGoods(){
      return goodRepository.findAllByActiveTrue();
   }
}
