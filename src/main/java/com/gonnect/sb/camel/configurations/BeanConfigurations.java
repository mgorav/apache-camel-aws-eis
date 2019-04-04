package com.gonnect.sb.camel.configurations;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.AwsRegionProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.amazonaws.regions.Regions.DEFAULT_REGION;
import static com.amazonaws.regions.Regions.US_EAST_1;
import static java.lang.System.getProperty;

@Configuration
public class BeanConfigurations {
    private AWSCredentials awsCredentials = new BasicAWSCredentials(getProperty("accessKey") == null ? "foobar" : getProperty("accessKey"),
            getProperty("secretKey") == null ? "foobar" : getProperty("secretKey"));
    @Bean
    public RandomDataGenerator randomDataGenerator() {
        return new RandomDataGenerator();
    }

    @Bean
    public AmazonS3 amazonS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials("XXXXX", "YYYYY");
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        AmazonS3ClientBuilder clientBuilder = AmazonS3ClientBuilder.standard().withPathStyleAccessEnabled(true)
                .disableChunkedEncoding()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://0.0.0.0:4572", US_EAST_1.getName()))
                .withCredentials(credentialsProvider);
        return clientBuilder.build();
    }

}
