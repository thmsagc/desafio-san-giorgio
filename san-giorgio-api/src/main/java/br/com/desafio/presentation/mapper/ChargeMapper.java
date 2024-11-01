package br.com.desafio.presentation.mapper;

import br.com.desafio.domain.model.Charge;
import br.com.desafio.application.generic.GenericMapper;
import br.com.desafio.presentation.dto.response.ChargeResponse;
import br.com.desafio.presentation.dto.request.NewChargeRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {})
public abstract class ChargeMapper implements GenericMapper<NewChargeRequest, ChargeResponse, Charge> {

}
