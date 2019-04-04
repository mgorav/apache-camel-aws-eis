package com.gonnect.sb.camel;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.CreateStreamRequest;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootCamelApplication implements CommandLineRunner {
    @Autowired
    private AmazonS3 s3Client;
    @Autowired
    private AmazonKinesis kinesisClient;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCamelApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
//        s3Client.createBucket("test-bucket");
//
//        CreateStreamRequest createStreamRequest = new CreateStreamRequest();
//        createStreamRequest.setStreamName( "mykinesisstream" );
//        createStreamRequest.setShardCount( 1 );
//
//        kinesisClient.createStream(createStreamRequest);

    }
}
