package com.avegarlabs.coninsuservice.services;


import com.avegarlabs.coninsuservice.dto.FormaterListItem;
import com.avegarlabs.coninsuservice.dto.FormaterModel;
import com.avegarlabs.coninsuservice.models.Formaters;
import com.avegarlabs.coninsuservice.repositories.FormaterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FormaterService {
 private final FormaterRepository repository;


  public List<FormaterListItem> getAllFormater(){
      return repository.findAll().stream().map(this::mapFormaterListItemToFormater).toList();
  }

  public FormaterListItem saveFormater(FormaterModel formaterModel){
      Formaters f = mapFormaterToFormaterModel(formaterModel);
      repository.save(f);
      return mapFormaterListItemToFormater(f);
  }

  public FormaterListItem updateFormater(String id, FormaterModel model){
      Formaters formater = repository.findById(id).get();
      formater.setType(model.getType());
      formater.setPrice(model.getPrice());
      repository.save(formater);
      return mapFormaterListItemToFormater(formater);
  }

  public void deleteFormater(String id){
      repository.deleteById(id);
  }

    public FormaterListItem mapFormaterListItemToFormater(Formaters formaters){
        return FormaterListItem.builder()
                .id(formaters.getId())
                .type(formaters.getType())
                .price(formaters.getPrice())
                .build();
    }

    public Formaters mapFormaterToFormaterModel(FormaterModel formaters){
        return Formaters.builder()
                .type(formaters.getType())
                .price(formaters.getPrice())
                .build();
    }

}
