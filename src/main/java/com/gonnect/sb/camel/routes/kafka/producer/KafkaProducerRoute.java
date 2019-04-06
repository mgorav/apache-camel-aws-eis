package com.gonnect.sb.camel.routes.kafka.producer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {


        from("timer:helloKafkaProducer?period={{timer.period}}")
                .routeGroup("kafka-route-group")
                .routeId("kafkaStartWithPartitioner")
                    .transform().method("springBean", "greet")
                    .filter(simple("${body} contains 'foo'"))
                    .to("log:foo")
                .end()
                .to("kafka:{{producer.topic}}?partitioner={{camel.component.kafka.configuration.partitioner}}")
                .log("publish message to kafka")
                .log("${body}");

    }
}
