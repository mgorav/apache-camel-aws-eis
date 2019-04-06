package com.gonnect.sb.camel.routes.cbr;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class CbrRoute extends RouteBuilder {

    @Value("classpath:input")
    private Resource resource;

    @Override
    public void configure() throws Exception {

        from("file:" + resource.getURI().getPath()).
                choice()
                .when().jsonpath("$[?(@.country == 'IND')]")
                    .to("stream:out")
                .when().jsonpath("$[?(@.country == 'NL')]")
                    .to("stream:out")
                .when().jsonpath("$[?(@.country == 'UK')]")
                    .to("stream:out")
                .when().jsonpath("$[?(@.country == 'US')]")
                    .to("stream:out")
                .end();
    }
}
