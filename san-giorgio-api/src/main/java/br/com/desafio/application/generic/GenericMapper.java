package br.com.desafio.application.generic;

import java.util.List;

public interface GenericMapper<REQUEST, RESPONSE, ENTITY> {

    ENTITY requestToEntity(REQUEST request);

    List<ENTITY> requestsToEntities(List<REQUEST> request);

    RESPONSE entityToResponse(ENTITY entity);

    List<RESPONSE> entitiesToResponses(List<ENTITY> entities);

}