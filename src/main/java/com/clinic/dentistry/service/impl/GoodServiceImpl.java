package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.models.Good;
import com.clinic.dentistry.repo.GoodRepository;
import com.clinic.dentistry.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

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
       //good.setActive(true);
       goodRepository.save(good);
   }

   @Override
    public void goodEdit(Long goodId, Good newData)
   {
       Optional<Good> optGood = goodRepository.findById(goodId);
       if (!optGood.isPresent()){
           return;
       }
       Good good = optGood.get();
       good.setName(newData.getName());
       good.setPrice(newData.getPrice());
       good.setActive(newData.isActive());
       goodRepository.save(good);
   }

   @Override
    public Good findGood(Long id){
        return goodRepository.findGoodById(id);
   }
}
