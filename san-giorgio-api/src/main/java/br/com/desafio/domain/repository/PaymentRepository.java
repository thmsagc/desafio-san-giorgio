package br.com.desafio.domain.repository;

import br.com.desafio.domain.model.Charge;
import br.com.desafio.domain.model.Payment;
import br.com.desafio.application.generic.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends GenericRepository<Payment> {

    List<Payment> findAllByCharge(Charge charge);

}
