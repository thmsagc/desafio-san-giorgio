package br.com.desafio.domain.repository;

import br.com.desafio.domain.model.Client;
import br.com.desafio.application.generic.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends GenericRepository<Client> {

}
