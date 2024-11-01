package br.com.desafio.application.service.implementation;

import br.com.desafio.application.service.ChargeService;
import br.com.desafio.domain.model.Charge;
import br.com.desafio.application.generic.GenericRepository;
import br.com.desafio.application.generic.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ChargeServiceImpl extends GenericServiceImpl<Charge> implements ChargeService {

    public ChargeServiceImpl(GenericRepository<Charge> repository) {
        super(repository);
    }

}
