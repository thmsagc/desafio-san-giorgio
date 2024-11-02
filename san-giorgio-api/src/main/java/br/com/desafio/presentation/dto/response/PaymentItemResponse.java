package br.com.desafio.presentation.dto.response;

import br.com.desafio.application.generic.GenericResponse;
import br.com.desafio.domain.shared.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentItemResponse extends GenericResponse {

    @JsonProperty("charge_id")
    private UUID chargeId;

    @JsonProperty("payment_value")
    @DecimalMin("0.01")
    private BigDecimal amount;

    private PaymentStatus status;

}
