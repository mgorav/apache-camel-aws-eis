package com.gonnect.sb.camel.routes.aws.kinesis;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.aws.kinesis.KinesisConstants;
import org.springframework.stereotype.Component;

@Component()
public class KinesisRouteProducer extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        from("direct:kinesisDirectRoute")
                .setHeader(KinesisConstants.PARTITION_KEY, simple("1"))
                .setHeader(KinesisConstants.SHARD_ID, simple("1"))
                .to("aws-kinesis://mykinesisstream?amazonKinesisClient=#amazonKinesisClient")
                .to("log:out?showAll=true")
                .log("Completed Writing to Kinesis using direct:kinesisDirectRoute ");
    }
}
