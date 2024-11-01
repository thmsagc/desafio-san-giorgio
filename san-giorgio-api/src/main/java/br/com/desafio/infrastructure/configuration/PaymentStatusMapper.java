package br.com.desafio.infrastructure.configuration;

import br.com.desafio.domain.shared.PaymentStatus;
import br.com.desafio.infrastructure.service.SqsPaidQueueService;
import br.com.desafio.infrastructure.service.SqsPartialQueueService;
import br.com.desafio.infrastructure.service.SqsQueueService;
import br.com.desafio.infrastructure.service.SqsSurplusQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentStatusMapper {

    private final Map<PaymentStatus, SqsQueueService> handlerMap = new HashMap<>();

    @Autowired
    public PaymentStatusMapper(SqsPaidQueueService paidHandler,
                               SqsSurplusQueueService surplusHandler,
                               SqsPartialQueueService partialHandler) {
        handlerMap.put(PaymentStatus.PAID, paidHandler);
        handlerMap.put(PaymentStatus.PARTIAL, surplusHandler);
        handlerMap.put(PaymentStatus.SURPLUS, partialHandler);
    }

    public SqsQueueService getHandler(PaymentStatus status) {
        return handlerMap.get(status);
    }
}