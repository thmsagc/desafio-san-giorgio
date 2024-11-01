package br.com.desafio.application.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface GenericRepository<E extends GenericEntity> extends JpaRepository<E, UUID>, JpaSpecificationExecutor<E> {

}
