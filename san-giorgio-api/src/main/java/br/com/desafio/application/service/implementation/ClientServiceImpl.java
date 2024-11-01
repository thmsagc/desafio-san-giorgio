package br.com.desafio.application.service.implementation;

import br.com.desafio.application.service.ClientService;
import br.com.desafio.domain.model.Client;
import br.com.desafio.domain.repository.ClientRepository;
import br.com.desafio.application.generic.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl extends GenericServiceImpl<Client> implements ClientService {

    public ClientServiceImpl(ClientRepository clientRepository) {
        super(clientRepository);
    }

}