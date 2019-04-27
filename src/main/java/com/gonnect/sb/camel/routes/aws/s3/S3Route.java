package com.gonnect.sb.camel.routes.aws.s3;

import com.amazonaws.regions.Regions;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.aws.s3.S3Constants;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A timer based Camel route that call REST service
 */
@Component
public class S3Route extends RouteBuilder {
    @Autowired
    private RandomDataGenerator randomDataGenerator;

    @Override
    public void configure() throws Exception {

        from("timer:trigger?period=12h")
                .routeId("RandomTextGeneratorRoute")
                .process(exchange -> {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < 1000; i++) {
                        String str = RandomStringUtils.randomAlphabetic(randomDataGenerator.nextInt(1, 10));
                        stringBuilder.append(str).append(" ");
                    }
                    exchange.getIn().setBody(stringBuilder.toString());
                })
                .log(LoggingLevel.INFO, "Started Uploading to S3 bucket")
                //set the required headers for file upload to aws s3
                .setHeader(S3Constants.CONTENT_LENGTH, simple("${body.length}"))
                .setHeader(S3Constants.KEY, constant("random-text.txt"))
                .setHeader(S3Constants.CONTENT_TYPE, constant("text/plain"))
                .setHeader(S3Constants.CONTENT_ENCODING, constant("UTF-8"))
                .setHeader(S3Constants.CANNED_ACL, constant("PublicRead"))
                .log(LoggingLevel.INFO, "File name used to upload : random-text.txt")
                .to("aws-s3://test-bucket?amazonS3Client=#amazonS3Client" + "&region=" + Regions.US_EAST_1)
                .log("Completed uploading to s3 bucket");


        //        from("timer:trigger?period=12h")
//                .routeId("RandomTextGeneratorRoute-1")
//                .process(exchange -> {
//                    StringBuilder stringBuilder = new StringBuilder();
//                    for (int i = 0; i < 1000; i++) {
//                        String str = RandomStringUtils.randomAlphabetic(randomDataGenerator.nextInt(1, 10));
//                        stringBuilder.append(str).append(" ");
//                        if ((i + 1) % 10 == 0) {
//                         stringBuilder.append("\n");
//                        }
//                    }
//                    exchange.getIn().setBody(stringBuilder.toString());
//                })
//                .log(LoggingLevel.INFO, "Started Uploading to S3 bucket")
//                //set the required headers for file upload to aws s3
//                .setHeader(S3Constants.CONTENT_LENGTH, simple("${body.length}"))
//                .setHeader(S3Constants.KEY, constant("random-text.txt"))
//                .setHeader(S3Constants.CONTENT_TYPE, constant("text/plain"))
//                .setHeader(S3Constants.CONTENT_ENCODING, constant("UTF-8"))
//                .setHeader(S3Constants.CANNED_ACL, constant("PublicRead"))
//                .log(LoggingLevel.INFO, "File name used to upload : random-text.txt")
//                .to("aws-s3://test-bucket?amazonS3Client=#amazonS3Client" + "&region=" + Regions.US_EAST_1)
//                .log("Completed uploading to s3 bucket")
//                .to("aws-s3://test-bucket?amazonS3Client=#amazonS3Client&prefix=random-text.txt" + "&region=" + Regions.US_EAST_1 + "&includeBody=true")
//                        .split(body().tokenize("\n"))
//                        .streaming()
//                        .to("stream:out")
//                .log("file downloaded successfully");

//
//         from("aws-s3://test-bucket?amazonS3Client=#amazonS3Client&prefix=random-text.txt" + "&region=" + Regions.US_EAST_1 + "&includeBody=true")
//                .split(body().tokenize("\n"))
//                .streaming()
//                .to("stream:out")
//                .log("file downloaded successfully");

//        from("direct:s3KafkaDirectRoute")
//                .routeGroup("s3-kafka-route-group")
//                .routeId("s3-kafka-route")
////                .setHeader("CamelAwsS3Key",constant("random-text.txt"))
////                .setHeader("CamelAwsS3BucketName",constant("test-bucket"))
////                .setHeader("CamelAwsS3ContentEncoding",constant("UTF-8"))
//                .log(LoggingLevel.INFO,"headers.bucketname")
//                .to("aws-s3://headers.bucketname?amazonS3Client=#amazonS3Client&prefix=random-text.txt" + "&region=" + Regions.US_EAST_1 + "&includeBody=true")
//                .split(body().tokenize("\n"))
//                .streaming()
//                .to("stream:out")
//                .log("file downloaded successfully");



//        CsvDataFormat csv = new CsvDataFormat();
//        csv.setLazyLoad(true);
//        csv.setUseMaps(true);
//
//
//
//        from("direct:s3KafkaDirectRoute")
//                .routeGroup("s3-kafka-route-group")
//                .routeId("s3-kafka-route")
//                .to("aws-s3://headers.bucketname?amazonS3Client=#amazonS3Client"+ "&region=" + US_EAST_1 + "&includeBody=true&autocloseBody=true")
////                .unmarshal(csv)
//                .split(body())
//                .streaming()
//                .to("stream:out");
////                .to("log:mappedRow?multiline=true")
////                .to("bean:csvHandler?method=doHandleCsv");
////                .to("direct:kafkaDirectRoute");


//        from("aws-s3://test-bucket?amazonS3Client=#amazonS3Client" + "&region=" + Regions.US_EAST_1)
//                .unmarshal(csv)
//                .split(body())
//                .streaming()
//                .to("log:mappedRow?multiline=true")
//                .to("bean:csvHandler?method=doHandleCsv")
//                .to("direct:kafkaDirectRoute/");
    }
}
