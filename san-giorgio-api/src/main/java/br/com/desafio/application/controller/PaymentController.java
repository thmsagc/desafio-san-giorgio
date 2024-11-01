package br.com.desafio.application.controller;


import br.com.desafio.application.service.PaymentService;
import br.com.desafio.domain.model.Payment;
import br.com.desafio.presentation.dto.request.PaymentRequest;
import br.com.desafio.presentation.dto.response.PaymentResponse;
import br.com.desafio.presentation.mapper.PaymentMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
public class PaymentController  {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @PostMapping(path = "/api/payments")
    public ResponseEntity<List<PaymentResponse>> processPayment(@Valid @RequestBody PaymentRequest request) {
        List<Payment> payments = paymentService.process(request);
        return ResponseEntity.status(HttpStatus.OK).body(paymentMapper.entitiesToResponses(payments));
    }

}