package com.avegarlabs.productionservice.services;

import com.avegarlabs.productionservice.dto.CoeficientListItem;
import com.avegarlabs.productionservice.dto.CoeficientModel;
import com.avegarlabs.productionservice.models.Coeficients;
import com.avegarlabs.productionservice.repositories.CoeficientsRepository;
import com.avegarlabs.productionservice.utils.DateData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoeficientsServices {
    private final CoeficientsRepository repository;
    private final DateData dateData;


    public List<CoeficientListItem> getCoeficientes() {
        return repository.findAll().stream().map(this::mapCoeficienteListItemToCoefients).toList();
    }

    public CoeficientListItem persisteCoeficient(CoeficientModel model){
        Coeficients coeficients = mapCoeficientToCoefientsModel(model);
        repository.save(coeficients);
        return mapCoeficienteListItemToCoefients(coeficients);
    }

    public CoeficientListItem updateCoeficients(String id, CoeficientModel coeficientModel){
        Coeficients c = repository.findById(id).get();
        c.setHoursCoeficient(coeficientModel.getHoursCoeficient());
        c.setProdCoeficient(coeficientModel.getProdCoeficient());
        c.setLastUpdateTime(dateData.today());
        repository.save(c);
        return mapCoeficienteListItemToCoefients(c);
    }

    public void removeCoeficiente(String id){
        repository.deleteById(id);
    }


    public CoeficientListItem mapCoeficienteListItemToCoefients(Coeficients coeficients) {
        return CoeficientListItem.builder()
                .id(coeficients.getId())
                .hoursCoeficient(coeficients.getHoursCoeficient())
                .prodCoeficient(coeficients.getProdCoeficient())
                .build();
    }

    public Coeficients mapCoeficientToCoefientsModel(CoeficientModel coeficients) {
        return Coeficients.builder()
                .hoursCoeficient(coeficients.getHoursCoeficient())
                .prodCoeficient(coeficients.getProdCoeficient())
                .creationDate(dateData.today())
                .lastUpdateTime(dateData.today())
                .build();
    }

}
