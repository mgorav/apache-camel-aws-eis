package com.gonnect.sb.camel.routes.kafka.consumer;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerRoute extends RouteBuilder {
    @Autowired
    private KafkaComponent kafkaComponent;

    @Override
    public void configure() throws Exception {
        kafkaComponent.setBrokers("{{camel.component.kafka.brokers}}");

        from("kafka:{{consumer.topic}}?brokers={{camel.component.kafka.brokers}}"
                + "&maxPollRecords={{camel.component.kafka.configuration.max-poll-records}}"
                + "&consumersCount={{camel.component.kafka.configuration.consumers-count}}"
                + "&seekTo={{camel.component.kafka.configuration.seek-to}}"
                + "&groupId={{camel.component.kafka.configuration.group-id}}")
                .routeId("FromKafka")
                .log("consumed message from Kafka")
                .log("${body}");


    }
}
