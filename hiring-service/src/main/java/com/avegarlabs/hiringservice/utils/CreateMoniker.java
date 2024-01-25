package com.avegarlabs.hiringservice.utils;

import org.springframework.stereotype.Component;

@Component
public class CreateMoniker {

    public String createMoniker(String input) {
        if (input == null) {
            return null;
        }
        String result = input.toLowerCase();
        result = result.replace("á", "a");
        result = result.replace("é", "e");
        result = result.replace("í", "i");
        result = result.replace("ó", "o");
        result = result.replace("ú", "u");
        result = result.replace("ñ", "n");

        return result.replace(" ", "-");
    }
}
