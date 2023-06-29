package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.models.Good;
import com.clinic.dentistry.repo.GoodRepository;
import com.clinic.dentistry.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ApiResponse goodSave(Good good){
       //good.setActive(true);
       goodRepository.save(good);
       return ApiResponse.builder()
               .status(HttpStatus.CREATED)
               .message("Услуга успешно создана")
               .build();
   }

   @Override
    public ApiResponse goodEdit(Long goodId, Good newData)
   {
       Optional<Good> optGood = goodRepository.findById(goodId);
       if (!optGood.isPresent()){
           return ApiResponse.builder()
                   .status(HttpStatus.NOT_FOUND)
                   .message("Редактируемая услуга не найдена")
                   .build();
       }
       Good good = optGood.get();
       good.setName(newData.getName());
       good.setPrice(newData.getPrice());
       good.setActive(newData.isActive());
       goodRepository.save(good);
       return ApiResponse.builder()
               .status(HttpStatus.OK)
               .message("Услуга успешно отредактирована")
               .build();
   }

   @Override
    public Good findGood(Long id){
        return goodRepository.findGoodById(id);
   }
}
