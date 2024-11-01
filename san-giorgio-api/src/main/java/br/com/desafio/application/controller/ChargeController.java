package br.com.desafio.application.controller;


import br.com.desafio.application.service.ChargeService;
import br.com.desafio.domain.model.Charge;
import br.com.desafio.application.generic.GenericController;
import br.com.desafio.presentation.dto.response.ChargeResponse;
import br.com.desafio.presentation.dto.request.NewChargeRequest;
import br.com.desafio.presentation.mapper.ChargeMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Tag(name = "Charges")
@RequestMapping("/api/charges")
@RestController
public class ChargeController extends GenericController<NewChargeRequest, ChargeResponse, Charge> {

    protected ChargeController(ChargeService service, ChargeMapper mapper) {
        super(service, mapper);
    }

}