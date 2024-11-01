package br.com.desafio.application.service.implementation;

import br.com.desafio.application.service.ChargeService;
import br.com.desafio.application.service.ClientService;
import br.com.desafio.application.service.PaymentService;
import br.com.desafio.domain.model.Charge;
import br.com.desafio.domain.model.Client;
import br.com.desafio.domain.model.Payment;
import br.com.desafio.domain.repository.PaymentRepository;
import br.com.desafio.domain.shared.PaymentStatus;
import br.com.desafio.application.generic.GenericRepository;
import br.com.desafio.application.generic.GenericServiceImpl;
import br.com.desafio.infrastructure.configuration.PaymentStatusMapper;
import br.com.desafio.infrastructure.exception.InternalServerErrorException;
import br.com.desafio.infrastructure.service.SqsQueueService;
import br.com.desafio.presentation.dto.request.PaymentRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl extends GenericServiceImpl<Payment> implements PaymentService {

    private final ClientService clientService;
    private final ChargeService chargeService;
    private final PaymentStatusMapper paymentStatusMapper;

    public PaymentServiceImpl(ClientService clientService,
                              ChargeService chargeService,
                              GenericRepository<Payment> repository, PaymentStatusMapper paymentStatusMapper) {
        super(repository);

        this.clientService = clientService;
        this.chargeService = chargeService;
        this.paymentStatusMapper = paymentStatusMapper;
    }

    @Override
    @Transactional
    public List<Payment> process(PaymentRequest request) {
        Map<Charge, BigDecimal> amountPaid = new HashMap<>();

        Client client = clientService.getById(request.getClientId());

        List<Payment> payments = request.getPaymentItems().stream().map(paymentItemRequest -> {
            Charge charge = chargeService.getById(paymentItemRequest.getChargeId());

            if(amountPaid.get(charge) != null) {
                amountPaid.put(charge, amountPaid.get(charge).add(paymentItemRequest.getAmount()));
            } else {
                amountPaid.put(charge, getPaidValueByCharge(charge).add(paymentItemRequest.getAmount()));
            }

            PaymentStatus paymentStatus = getPaymentStatusByAmountPaid(charge, amountPaid);

            return Payment.builder()
                    .client(client)
                    .charge(charge)
                    .amount(paymentItemRequest.getAmount())
                    .status(paymentStatus).build();
        }).toList();

        List<Payment> paymentsPersisted = this.repository.saveAll(payments);

        paymentsPersisted.forEach(this::sendPaymentToQueue);

        return paymentsPersisted;
    }

    public PaymentStatus getPaymentStatusByAmountPaid(Charge charge, Map<Charge, BigDecimal> amountPaid) {
        PaymentStatus status;

        if(amountPaid.get(charge).compareTo(charge.getAmount()) < 0) {
            status = PaymentStatus.PARTIAL;
        } else if(amountPaid.get(charge).compareTo(charge.getAmount()) == 0) {
            status = PaymentStatus.PAID;
        } else {
            status = PaymentStatus.SURPLUS;
        }

        return status;
    }

    public BigDecimal getPaidValueByCharge(Charge charge) {
        return ((PaymentRepository) this.repository).findAllByCharge(charge)
                .stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void sendPaymentToQueue(Payment payment) {
        SqsQueueService handler = paymentStatusMapper.getHandler(payment.getStatus());
        if (handler != null) {
            try {
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String message = ow.writeValueAsString(payment);
                handler.sendMessage(message);
            } catch (JsonProcessingException e) {
                throw new InternalServerErrorException("Error sending payment to corresponding queue.");
            }
        } else {
            throw new IllegalArgumentException("Unknown payment status: " + payment.getStatus());
        }
    }

}
