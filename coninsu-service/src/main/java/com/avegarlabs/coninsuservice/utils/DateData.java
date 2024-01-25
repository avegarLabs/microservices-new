package com.avegarlabs.coninsuservice.utils;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class DateData {
    public Date today(){
        LocalDate localDate = LocalDate.now();
        return Date.valueOf(localDate);
    }
    public String currentMonth(){
        LocalDate localDate = LocalDate.now();
        return localDate.getMonth().toString()+"/"+localDate.getYear();
    }
}
