package br.com.desafio.infrastructure.service;

import br.com.desafio.infrastructure.service.abstraction.AbstractSqsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;

@Service
public class SqsSurplusQueueService extends AbstractSqsService implements SqsQueueService {

    @Value("${aws.sqs.queues.surplusQueue}")
    private String queueUrl;

    protected SqsSurplusQueueService(SqsClient sqsClient) {
        super(sqsClient);
    }

    public void sendMessage(String messageBody) {
        super.sendMessage(queueUrl, messageBody);
    }
}