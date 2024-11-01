package br.com.desafio.application.service;

import br.com.desafio.application.service.implementation.PaymentServiceImpl;
import br.com.desafio.domain.model.Charge;
import br.com.desafio.domain.model.Client;
import br.com.desafio.domain.model.Payment;
import br.com.desafio.domain.repository.PaymentRepository;
import br.com.desafio.domain.shared.PaymentStatus;
import br.com.desafio.infrastructure.configuration.PaymentStatusMapper;
import br.com.desafio.infrastructure.service.SqsQueueService;
import br.com.desafio.presentation.dto.request.PaymentItemRequest;
import br.com.desafio.presentation.dto.request.PaymentRequest;
import br.com.desafio.presentation.mapper.PaymentMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private ClientService clientService;

    @Mock
    private ChargeService chargeService;

    @Mock
    private PaymentRepository repository;

    @Mock
    private PaymentMapper mapper;

    @Mock
    private PaymentStatusMapper paymentStatusMapper;

    @Mock
    private SqsQueueService sqsQueueService;

    private ObjectWriter objectWriter;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @Test
    void testProcess_partialPaymentStatus() {
        // Arrange
        Client client = new Client();
        Charge charge = new Charge();
        charge.setAmount(BigDecimal.valueOf(100));

        UUID chargeId = UUID.randomUUID();
        PaymentItemRequest paymentItemRequest = new PaymentItemRequest();
        paymentItemRequest.setChargeId(chargeId);
        paymentItemRequest.setAmount(BigDecimal.valueOf(50));

        UUID clientId = UUID.randomUUID();
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setClientId(clientId);
        paymentRequest.setPaymentItems(List.of(paymentItemRequest));

        when(clientService.getById(clientId)).thenReturn(client);
        when(chargeService.getById(chargeId)).thenReturn(charge);

        List<Payment> expectedPayments = new ArrayList<>();
        Payment partialPayment = Payment.builder()
                .client(client)
                .charge(charge)
                .amount(BigDecimal.valueOf(50))
                .status(PaymentStatus.PARTIAL)
                .build();
        expectedPayments.add(partialPayment);

        when(repository.saveAll(anyList())).thenReturn(expectedPayments);

        when(paymentStatusMapper.getHandler(PaymentStatus.PARTIAL)).thenReturn(sqsQueueService);

        SqsQueueService partialQueueService = mock(SqsQueueService.class);
        when(paymentStatusMapper.getHandler(PaymentStatus.PARTIAL)).thenReturn(partialQueueService);

        // Act
        List<Payment> payments = paymentService.process(paymentRequest);

        // Assert
        verify(clientService, times(1)).getById(clientId);
        verify(chargeService, times(1)).getById(chargeId);
        verify(repository, times(1)).saveAll(payments);

        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(partialQueueService, times(1)).sendMessage(messageCaptor.capture());

        assertEquals(1, payments.size());
        Payment payment = payments.get(0);
        assertEquals(client, payment.getClient());
        assertEquals(charge, payment.getCharge());
        assertEquals(BigDecimal.valueOf(50), payment.getAmount());
        assertEquals(PaymentStatus.PARTIAL, payment.getStatus());
        assertNotNull(messageCaptor.getValue());
        assertTrue(messageCaptor.getValue().contains(PaymentStatus.PARTIAL.toString()));
    }

    @Test
    void testProcess_paidPaymentStatus() {
        // Arrange
        Client client = new Client();
        Charge charge = new Charge();
        charge.setAmount(BigDecimal.valueOf(100));

        UUID chargeId = UUID.randomUUID();
        PaymentItemRequest paymentItemRequest = new PaymentItemRequest();
        paymentItemRequest.setChargeId(chargeId);
        paymentItemRequest.setAmount(BigDecimal.valueOf(100));

        UUID clientId = UUID.randomUUID();
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setClientId(clientId);
        paymentRequest.setPaymentItems(List.of(paymentItemRequest));

        when(clientService.getById(clientId)).thenReturn(client);
        when(chargeService.getById(chargeId)).thenReturn(charge);

        List<Payment> expectedPayments = new ArrayList<>();
        Payment partialPayment = Payment.builder()
                .client(client)
                .charge(charge)
                .amount(BigDecimal.valueOf(100))
                .status(PaymentStatus.PAID)
                .build();
        expectedPayments.add(partialPayment);

        when(repository.saveAll(anyList())).thenReturn(expectedPayments);

        when(paymentStatusMapper.getHandler(PaymentStatus.PAID)).thenReturn(sqsQueueService);

        SqsQueueService paidQueueService = mock(SqsQueueService.class);
        when(paymentStatusMapper.getHandler(PaymentStatus.PAID)).thenReturn(paidQueueService);

        // Act
        List<Payment> payments = paymentService.process(paymentRequest);

        // Assert
        verify(clientService, times(1)).getById(clientId);
        verify(chargeService, times(1)).getById(chargeId);
        verify(repository, times(1)).saveAll(payments);

        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(paidQueueService, times(1)).sendMessage(messageCaptor.capture());

        assertEquals(1, payments.size());
        Payment payment = payments.get(0);
        assertEquals(client, payment.getClient());
        assertEquals(charge, payment.getCharge());
        assertEquals(BigDecimal.valueOf(100), payment.getAmount());
        assertEquals(PaymentStatus.PAID, payment.getStatus());
        assertNotNull(messageCaptor.getValue());
        assertTrue(messageCaptor.getValue().contains(PaymentStatus.PAID.toString()));
    }

    @Test
    void testProcess_surplusPaymentStatus() {
        // Arrange
        Client client = new Client();
        Charge charge = new Charge();
        charge.setAmount(BigDecimal.valueOf(100));

        UUID chargeId = UUID.randomUUID();
        PaymentItemRequest paymentItemRequest = new PaymentItemRequest();
        paymentItemRequest.setChargeId(chargeId);
        paymentItemRequest.setAmount(BigDecimal.valueOf(150));

        UUID clientId = UUID.randomUUID();
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setClientId(clientId);
        paymentRequest.setPaymentItems(List.of(paymentItemRequest));

        when(clientService.getById(clientId)).thenReturn(client);
        when(chargeService.getById(chargeId)).thenReturn(charge);

        List<Payment> expectedPayments = new ArrayList<>();
        Payment partialPayment = Payment.builder()
                .client(client)
                .charge(charge)
                .amount(BigDecimal.valueOf(150))
                .status(PaymentStatus.SURPLUS)
                .build();
        expectedPayments.add(partialPayment);

        when(repository.saveAll(anyList())).thenReturn(expectedPayments);

        when(paymentStatusMapper.getHandler(PaymentStatus.SURPLUS)).thenReturn(sqsQueueService);

        SqsQueueService surplusQueueService = mock(SqsQueueService.class);
        when(paymentStatusMapper.getHandler(PaymentStatus.SURPLUS)).thenReturn(surplusQueueService);

        // Act
        List<Payment> payments = paymentService.process(paymentRequest);

        // Assert
        verify(clientService, times(1)).getById(clientId);
        verify(chargeService, times(1)).getById(chargeId);
        verify(repository, times(1)).saveAll(payments);

        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(surplusQueueService, times(1)).sendMessage(messageCaptor.capture());

        assertEquals(1, payments.size());
        Payment payment = payments.get(0);
        assertEquals(client, payment.getClient());
        assertEquals(charge, payment.getCharge());
        assertEquals(BigDecimal.valueOf(150), payment.getAmount());
        assertEquals(PaymentStatus.SURPLUS, payment.getStatus());
        assertNotNull(messageCaptor.getValue());
        assertTrue(messageCaptor.getValue().contains(PaymentStatus.SURPLUS.toString()));
    }

    @Test
    void testGetPaymentStatusByAmountPaid_PARTIAL() {
        Charge charge = new Charge();
        charge.setAmount(BigDecimal.valueOf(100));

        Map<Charge, BigDecimal> amountPaid = new HashMap<>();
        amountPaid.put(charge, BigDecimal.valueOf(50));

        PaymentStatus status = paymentService.getPaymentStatusByAmountPaid(charge, amountPaid);

        assertEquals(PaymentStatus.PARTIAL, status);
    }

    @Test
    void testGetPaymentStatusByAmountPaid_PAID() {
        Charge charge = new Charge();
        charge.setAmount(BigDecimal.valueOf(100));
        Map<Charge, BigDecimal> amountPaid = new HashMap<>();
        amountPaid.put(charge, BigDecimal.valueOf(100));

        PaymentStatus status = paymentService.getPaymentStatusByAmountPaid(charge, amountPaid);

        assertEquals(PaymentStatus.PAID, status);
    }

    @Test
    void testGetPaymentStatusByAmountPaid_SURPLUS() {
        Charge charge = new Charge();
        charge.setAmount(BigDecimal.valueOf(100));

        Map<Charge, BigDecimal> amountPaid = new HashMap<>();
        amountPaid.put(charge, BigDecimal.valueOf(150));

        PaymentStatus status = paymentService.getPaymentStatusByAmountPaid(charge, amountPaid);

        assertEquals(PaymentStatus.SURPLUS, status);
    }

    @Test
    void testSendPaymentToQueue_Success_PAID() {

        Payment payment = Payment.builder()
                .client(new Client())
                .charge(new Charge())
                .amount(BigDecimal.TEN)
                .status(PaymentStatus.PAID)
                .build();

        when(paymentStatusMapper.getHandler(PaymentStatus.PAID)).thenReturn(sqsQueueService);

        // Act
        paymentService.sendPaymentToQueue(payment);

        // Assert
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(sqsQueueService, times(1)).sendMessage(messageCaptor.capture());
        assertNotNull(messageCaptor.getValue());
        assertTrue(messageCaptor.getValue().contains("\"status\" : \"PAID\""));
    }

    @Test
    void testSendPaymentToQueue_Success_PARTIAL() {

        Payment payment = Payment.builder()
                .client(new Client())
                .charge(new Charge())
                .amount(BigDecimal.TEN)
                .status(PaymentStatus.PARTIAL)
                .build();

        when(paymentStatusMapper.getHandler(PaymentStatus.PARTIAL)).thenReturn(sqsQueueService);

        // Act
        paymentService.sendPaymentToQueue(payment);

        // Assert
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(sqsQueueService, times(1)).sendMessage(messageCaptor.capture());
        assertNotNull(messageCaptor.getValue());
        assertTrue(messageCaptor.getValue().contains("\"status\" : \"PARTIAL\""));
    }

    @Test
    void testSendPaymentToQueue_Success_SURPLUS() {

        Payment payment = Payment.builder()
                .client(new Client())
                .charge(new Charge())
                .amount(BigDecimal.TEN)
                .status(PaymentStatus.SURPLUS)
                .build();

        when(paymentStatusMapper.getHandler(PaymentStatus.SURPLUS)).thenReturn(sqsQueueService);

        // Act
        paymentService.sendPaymentToQueue(payment);

        // Assert
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(sqsQueueService, times(1)).sendMessage(messageCaptor.capture());
        assertNotNull(messageCaptor.getValue());
        assertTrue(messageCaptor.getValue().contains("\"status\" : \"SURPLUS\""));
    }

}
