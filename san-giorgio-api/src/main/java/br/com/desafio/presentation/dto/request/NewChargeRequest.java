package br.com.desafio.presentation.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewChargeRequest {

    private BigDecimal amount;

}
