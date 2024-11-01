package br.com.desafio.application.generic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface GenericService<E extends GenericEntity> {

    E getById(UUID id);
    Page<E> list(Pageable pageable);
    E save(E E);
    List<E> saveAll(List<E> E);
    E persist(E E);
    List<E> persistAll(List<E> entities);
    void delete(E E);
    void deleteAll(List<E> entities);

}
