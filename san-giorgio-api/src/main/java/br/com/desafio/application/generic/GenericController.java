package br.com.desafio.application.generic;

import br.com.desafio.application.util.PageUtils;
import br.com.desafio.presentation.pageable.PageableParams;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
public class GenericController<REQUEST, RESPONSE extends GenericResponse, ENTITY extends GenericEntity> {

    protected final GenericService<ENTITY> service;
    protected final GenericMapper<REQUEST, RESPONSE, ENTITY> mapper;

    protected GenericController(GenericService<ENTITY> service, GenericMapper<REQUEST, RESPONSE, ENTITY> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(List.class, new CustomCollectionEditor(List.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RESPONSE> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(mapper.entityToResponse(service.getById(id)));
    }

    @PostMapping
    public ResponseEntity<RESPONSE> add(@Valid @RequestBody REQUEST request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.entityToResponse(service.save(mapper.requestToEntity(request))));
    }

    @GetMapping
    public ResponseEntity<Page<RESPONSE>> list(@ParameterObject PageableParams pageableParams) {

        Pageable pageable = PageUtils.getPageable(pageableParams);
        Page<ENTITY> entities = service.list(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageImpl<>(
                        mapper.entitiesToResponses(entities.getContent()), pageable, entities.getTotalElements()));
    }

}