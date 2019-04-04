package com.gonnect.sb.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * A timer based Camel route that call REST service
 */
@Component
public class RestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration().host("localhost").port(4001);

        from("timer:hello?period={{timer.period}}")
                .setHeader("id", simple("${random(1,3)}"))
                .to("rest:get:cars/{id}")
                .log("${body}");

    }
}
