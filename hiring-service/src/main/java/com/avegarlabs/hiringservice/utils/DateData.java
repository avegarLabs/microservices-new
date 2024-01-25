package com.avegarlabs.hiringservice.utils;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Component
public class DateData {
    private Date date;

    public Date today() {
        LocalDate localDate = LocalDate.now();
        return Date.valueOf(localDate);
    }

    public String currentMonth() {
        LocalDate localDate = LocalDate.now();
        return localDate.getMonth().toString() + "/" + localDate.getYear();
    }

    public Date checkDate(String dtoDate) {
        if (dtoDate == null || dtoDate.isEmpty()) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.util.Date utilDate = formatter.parse(dtoDate);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            // Manejar la excepción si la fecha no está en el formato esperado
            return null;
        }
    }

}
