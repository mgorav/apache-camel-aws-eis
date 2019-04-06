package com.gonnect.sb.camel.controllers;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

@RestController
public class CarController {

    private static  String[] CARS = new String[]{"BMW", "TESLA", "AUDI"};

    @EndpointInject(uri = "direct:kinesisDirectRoute")
    private ProducerTemplate kinesisProducer;

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

    /**
     * POST to this URL will routed to AWS Kinesis
     * @param car
     */
    @PostMapping(value = "/cars")
    public void create(@RequestBody String car) {

        kinesisProducer.sendBody("direct:kinesisDirectRoute",car);
    }
}
