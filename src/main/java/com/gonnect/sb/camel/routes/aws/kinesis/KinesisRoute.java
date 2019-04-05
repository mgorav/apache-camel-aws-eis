package com.gonnect.sb.camel.routes.aws.kinesis;

import com.amazonaws.regions.Regions;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.ExpressionClause;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.aws.kinesis.KinesisConstants;
import org.apache.camel.component.aws.s3.S3Constants;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A timer based Camel route that call REST service
 */
@Component
public class KinesisRoute extends RouteBuilder {
    @Autowired
    private RandomDataGenerator randomDataGenerator;

    @Override
    public void configure() throws Exception {

        restConfiguration().host("localhost").port(4001);

        from("timer:hi?period={{timer.period}}")
                .setHeader("id", simple("${random(1,3)}"))
                .to("rest:get:cars/{id}")
                .log("[going to Kinesis]"+"${body}")
                .setHeader(KinesisConstants.PARTITION_KEY,simple("1"))
                .setHeader(KinesisConstants.SHARD_ID, simple("1"))
                .to("aws-kinesis://mykinesisstream?amazonKinesisClient=#amazonKinesisClient")
                .to("log:out?showAll=true")
                .log("Completed Writing to Kinesis");



    }
}
