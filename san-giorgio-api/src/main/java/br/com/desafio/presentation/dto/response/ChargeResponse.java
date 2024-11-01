package br.com.desafio.presentation.dto.response;

import br.com.desafio.application.generic.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChargeResponse extends GenericResponse {

    private BigDecimal amount;

}
