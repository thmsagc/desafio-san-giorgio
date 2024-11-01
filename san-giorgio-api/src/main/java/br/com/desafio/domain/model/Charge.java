package br.com.desafio.domain.model;

import br.com.desafio.application.generic.GenericEntity;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Charge extends GenericEntity {

    private BigDecimal amount;

}
