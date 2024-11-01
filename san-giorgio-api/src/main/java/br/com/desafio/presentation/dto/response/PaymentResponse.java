package br.com.desafio.presentation.dto.response;

import br.com.desafio.domain.shared.PaymentStatus;
import br.com.desafio.application.generic.GenericResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentResponse extends GenericResponse {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("client_id")
    private UUID clientId;

    @JsonProperty("charge_id")
    private UUID chargeId;

    private BigDecimal amount;

    private PaymentStatus status;

}
