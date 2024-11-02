package br.com.desafio.infrastructure.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;

@Configuration
public class SqsLocalStackInitializer {

    @Value("${aws.sqs.endpoint:#{null}}")
    private String endpoint;

    private final SqsClient sqsClient;

    public SqsLocalStackInitializer(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @PostConstruct
    public void createQueues() {
        if(endpoint != null) {
            this.createQueue("partialQueue");
            this.createQueue("paidQueue");
            this.createQueue("surplusQueue");
        }
    }

    public void createQueue(String queueName) {
        CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                .queueName(queueName)
                .build();

        sqsClient.createQueue(createQueueRequest);
    }
}