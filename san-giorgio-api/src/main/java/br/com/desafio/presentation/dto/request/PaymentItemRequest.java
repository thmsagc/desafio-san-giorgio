package br.com.desafio.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentItemRequest {

    @JsonProperty("charge_id")
    private UUID chargeId;

    @JsonProperty("payment_value")
    @DecimalMin("0.01")
    private BigDecimal amount;

}
