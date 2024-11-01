package br.com.desafio.application.controller;


import br.com.desafio.application.service.ClientService;
import br.com.desafio.domain.model.Client;
import br.com.desafio.application.generic.GenericController;
import br.com.desafio.presentation.dto.request.*;
import br.com.desafio.presentation.dto.response.ClientResponse;
import br.com.desafio.presentation.mapper.ClientMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Tag(name = "Clients")
@RequestMapping("/api/clients")
@RestController
public class ClientController extends GenericController<NewClientRequest, ClientResponse, Client> {

    protected ClientController(ClientService service, ClientMapper mapper) {
        super(service, mapper);
    }

}