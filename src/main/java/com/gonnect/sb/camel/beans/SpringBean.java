package com.gonnect.sb.camel.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("springBean")
public class SpringBean {

    @Value("${greeting}")
    private String greeting;

    public String greet() {
        return greeting;
    }
}
