package br.com.desafio.application.service;

import br.com.desafio.domain.model.Payment;
import br.com.desafio.application.generic.GenericService;
import br.com.desafio.presentation.dto.request.PaymentRequest;

import java.util.List;

public interface PaymentService extends GenericService<Payment> {

    List<Payment> process(PaymentRequest request);

}
