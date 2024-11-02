package br.com.desafio.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PaymentRequest {

    @JsonProperty("client_id")
    @NotNull
    private UUID clientId;

    @JsonProperty("payment_items")
    @NotNull
    private List<@Valid PaymentItemRequest> paymentItems;

}
