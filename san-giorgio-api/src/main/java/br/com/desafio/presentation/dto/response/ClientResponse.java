package br.com.desafio.presentation.dto.response;

import br.com.desafio.application.generic.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientResponse extends GenericResponse {

    private String name;

}
