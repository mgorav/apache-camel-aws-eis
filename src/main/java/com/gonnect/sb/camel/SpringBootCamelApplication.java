package com.gonnect.sb.camel;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootCamelApplication implements CommandLineRunner {
    @Autowired
    private AmazonS3 s3Client;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCamelApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        s3Client.createBucket("test-bucket");
    }
}
