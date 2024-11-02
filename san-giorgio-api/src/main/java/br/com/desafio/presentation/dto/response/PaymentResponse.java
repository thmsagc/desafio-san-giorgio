package br.com.desafio.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PaymentResponse {

    @JsonProperty("client_id")
    private UUID clientId;

    @JsonProperty("payment_items")
    private List<@Valid PaymentItemResponse> paymentItems;

}
