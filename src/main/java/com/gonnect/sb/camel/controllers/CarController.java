package com.gonnect.sb.camel.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

@RestController
public class CarController {

    private static final String[] CARS = new String[]{"BMW", "TESLA", "AUDI"};

    @GetMapping(value = "/cars/{id}")
    public Map<String, String> carById(@PathVariable("id") Integer id) {
        if (id != null && id > 0 && id <= CARS.length + 1) {
            int index = id - 1;
            String car = CARS[index];
            return singletonMap("name", car);
        } else {
            return emptyMap();
        }
    }
}
