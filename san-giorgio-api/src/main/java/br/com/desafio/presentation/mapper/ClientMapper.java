package br.com.desafio.presentation.mapper;

import br.com.desafio.domain.model.Client;
import br.com.desafio.application.generic.GenericMapper;
import br.com.desafio.presentation.dto.response.ClientResponse;
import br.com.desafio.presentation.dto.request.NewClientRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {})
public abstract class ClientMapper implements GenericMapper<NewClientRequest, ClientResponse, Client> {

}
