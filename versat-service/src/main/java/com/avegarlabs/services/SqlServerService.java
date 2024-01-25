package com.avegarlabs.services;

import com.avegarlabs.dto.DatNomListItem;
import com.avegarlabs.dto.NomQueryItem;
import com.avegarlabs.repositories.SQLServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class SqlServerService {

    @Autowired
    private SQLServerRepository repository;

    @Cacheable(value = "datNomValues", unless = "#result == null")
    public List<DatNomListItem> getVERSATNomModuleData() {
         return repository.readNomData().stream().map(this::mapToNomQueryItem).toList();
    }
    public  DatNomListItem mapToNomQueryItem(NomQueryItem model){
        return DatNomListItem.builder()
                .ci(model.getCi())
                .codigo_interno(model.getCodigo())
                .hours(model.getTotalTiempoEntrado())
                .salary(calcDev(model))
                .rate(calRealTar(model))
                .build();
    }

    private BigDecimal calRealTar(NomQueryItem model) {
        double salCal = calcDev(model).doubleValue();
        double tar = salCal / model.getTotalTiempoEntrado().doubleValue();
        return  new BigDecimal(tar).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcDev(NomQueryItem model) {
        double calculo = model.getTotalCobrar().doubleValue() + model.getTotalBonificaciones().doubleValue() - model.getTotalPenalizaciones().doubleValue();
        return  new BigDecimal(calculo).setScale(2, RoundingMode.HALF_UP);
    }

}
