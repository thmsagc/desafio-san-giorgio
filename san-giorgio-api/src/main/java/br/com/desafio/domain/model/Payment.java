package br.com.desafio.domain.model;

import br.com.desafio.domain.shared.PaymentStatus;
import br.com.desafio.application.generic.GenericEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Payment extends GenericEntity {

    @ManyToOne
    @JoinColumn(name = "charge_id")
    private Charge charge;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

}