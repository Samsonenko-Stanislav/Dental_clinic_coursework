package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.models.Good;
import com.clinic.dentistry.repo.GoodRepository;
import com.clinic.dentistry.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GoodServiceImpl implements GoodService {
    @Autowired
    private GoodRepository goodRepository;

    @Override
    public Iterable<Good> findActiveGoods(){
      return goodRepository.findAllByActiveTrue();
   }

   @Override
   public Iterable<Good> findAllGoods(){
        return goodRepository.findAll();
   }

   @Override
    public void goodSave(Good good){
       good.setActive(true);
       goodRepository.save(good);
   }

   @Override
    public void goodEdit(Good good, Good good_new)
   {
       good.setName(good_new.getName());
       good.setPrice(good_new.getPrice());
       good.setActive(good_new.isActive());
       goodRepository.save(good);
   }

   @Override
    public Good findGood(Long id){
        return goodRepository.findGoodById(id);
   }
}
