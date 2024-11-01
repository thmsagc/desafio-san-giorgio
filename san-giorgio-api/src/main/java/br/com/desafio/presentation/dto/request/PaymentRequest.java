package br.com.desafio.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
public class PaymentRequest {

    @JsonProperty("client_id")
    private UUID clientId;

    @JsonProperty("payment_items")
    private List<@Valid PaymentItemRequest> paymentItems;

}
