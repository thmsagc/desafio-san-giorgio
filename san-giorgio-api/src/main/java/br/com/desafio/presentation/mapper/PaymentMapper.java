package br.com.desafio.presentation.mapper;

import br.com.desafio.domain.model.Payment;
import br.com.desafio.application.generic.GenericMapper;
import br.com.desafio.presentation.dto.request.PaymentItemRequest;
import br.com.desafio.presentation.dto.response.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {})
public abstract class PaymentMapper implements GenericMapper<PaymentItemRequest, PaymentResponse, Payment> {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "charge.id", target = "chargeId")
    public abstract PaymentResponse entityToResponse(Payment payment);

}
