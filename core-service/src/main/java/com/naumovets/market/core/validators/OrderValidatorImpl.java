package com.naumovets.market.core.validators;

import com.naumovets.market.api.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class OrderValidatorImpl implements OrderValidator {

    @Override
    public void validate(String username, String phone, String address) {
        List<String> errorMessage = new ArrayList<>();
        if (username.equals("undefined") || username.length() <= 1 || username.length() > 255) {
            errorMessage.add("Имя пользователя не может быть пустым или иметь длину меньше 1 или больше 255 символов");
        }
        if (phone.equals("undefined") || phone.length() <= 1 || username.length() > 16) {
            errorMessage.add("Телефон не может быть пустым или иметь длину меньше 1 или больше 16 символов");
        }
        if (address.equals("undefined") || address.length() <= 1) {
            errorMessage.add("Адрес не может быть пустым или иметь длину меньше 1 символа");
        }

        String[] splitAddress = address.split(", ");

        String country = splitAddress[0];
        String city = splitAddress[1].substring(2);
        String street = splitAddress[2].substring(3);
        String home = splitAddress[3].substring(4);
        String flat = splitAddress[4].substring(9);

        Map<String, String> orderForm = new HashMap<>();

        orderForm.put("Страна", country);
        orderForm.put("Город", city);
        orderForm.put("Улица", street);
        orderForm.put("Дом", home);
        orderForm.put("Квартира", flat);

        for (String key : orderForm.keySet()) {
            String value = orderForm.get(key);
            if (value.equals("undefined")) {
                errorMessage.add("Заполните поле " + key);
            }
        }

        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }
}