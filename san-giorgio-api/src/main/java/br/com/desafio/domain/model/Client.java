package br.com.desafio.domain.model;

import br.com.desafio.application.generic.GenericEntity;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Client extends GenericEntity {

    private String name;

}
