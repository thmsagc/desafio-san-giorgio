package br.com.desafio.application.generic;

import br.com.desafio.infrastructure.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.UUID;

public class GenericServiceImpl<E extends GenericEntity> implements GenericService<E> {

    protected GenericRepository<E> repository;

    public GenericServiceImpl(GenericRepository<E> repository) {
        this.repository = repository;
    }

    @Override
    public E getById(UUID id) {
        return this.repository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        getGenericName().substring(0, 1).toUpperCase()
                                + getGenericName().substring(1) + " not found.")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<E> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional
    public E save(E e) {
        return this.persist(e);
    }

    @Override
    @Transactional
    public List<E> saveAll(List<E> entities) {
        return entities.stream().map(this::save).toList();
    }

    @Override
    @Transactional
    public E persist(E E) {
        return this.repository.save(E);
    }

    @Override
    @Transactional
    public List<E> persistAll(List<E> entities) {
        return entities.stream().map(this::persist).toList();
    }

    @Override
    @Transactional
    public void delete(E E) {
        this.repository.delete(E);
    }

    @Override
    @Transactional
    public void deleteAll(List<E> entities) {
        this.repository.deleteAll(entities);
    }

    protected String getGenericName()
    {
        String[] className = ((Class<E>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]).getTypeName().toLowerCase().split("\\.");
        return className[className.length-1];
    }
    
}
