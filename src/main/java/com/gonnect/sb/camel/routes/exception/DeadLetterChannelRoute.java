package com.gonnect.sb.camel.routes.exception;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Global Exception handling route
 */
@Component
public class DeadLetterChannelRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        errorHandler(deadLetterChannel("log:dead?level=ERROR")
                .maximumRedeliveries(3).redeliveryDelay(1000)
                .logStackTrace(true)
                .retryAttemptedLogLevel(LoggingLevel.ERROR));
    }
}
