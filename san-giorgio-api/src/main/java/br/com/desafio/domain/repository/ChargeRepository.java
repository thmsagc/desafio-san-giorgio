package br.com.desafio.domain.repository;

import br.com.desafio.domain.model.Charge;
import br.com.desafio.application.generic.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeRepository extends GenericRepository<Charge> {

}
