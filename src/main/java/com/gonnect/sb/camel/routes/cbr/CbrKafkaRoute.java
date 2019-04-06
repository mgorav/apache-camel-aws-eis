package com.gonnect.sb.camel.routes.cbr;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class CbrKafkaRoute extends RouteBuilder {

    @Value("classpath:kafkainput")
    private Resource resource;

    @Override
    public void configure() throws Exception {

        from("file:" + resource.getURI().getPath()).
                choice()
                .when().jsonpath("$[?(@.country == 'IND')]")
                    .to("kafka:{{producer.topic}}?partitioner={{camel.component.kafka.configuration.partitioner}}")
                    .log("[CBR] publish message to kafka")
                    .log("[Routed Payload]: "+"${body}")
                .when().jsonpath("$[?(@.country == 'NL')]")
                    .to("kafka:{{producer.topic}}?partitioner={{camel.component.kafka.configuration.partitioner}}")
                    .log("[CBR] publish message to kafka")
                    .log("[Routed Payload]: "+"${body}")
                .when().jsonpath("$[?(@.country == 'UK')]")
                     .to("kafka:{{producer.topic}}?partitioner={{camel.component.kafka.configuration.partitioner}}")
                    .log("[CBR] publish message to kafka")
                    .log("[Routed Payload]: "+"${body}")
                .when().jsonpath("$[?(@.country == 'US')]")
                    .to("kafka:{{producer.topic}}?partitioner={{camel.component.kafka.configuration.partitioner}}")
                    .log("[CBR] publish message to kafka")
                    .log("[Routed Payload]: "+"${body}")
                .end();
    }
}
