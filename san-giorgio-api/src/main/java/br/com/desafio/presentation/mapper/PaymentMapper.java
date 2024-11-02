package br.com.desafio.presentation.mapper;

import br.com.desafio.application.generic.GenericMapper;
import br.com.desafio.domain.model.Payment;
import br.com.desafio.presentation.dto.request.PaymentItemRequest;
import br.com.desafio.presentation.dto.response.PaymentItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {})
public abstract class PaymentMapper implements GenericMapper<PaymentItemRequest, PaymentItemResponse, Payment> {

    @Mapping(source = "charge.id", target = "chargeId")
    public abstract PaymentItemResponse entityToResponse(Payment payment);

}
