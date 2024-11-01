package br.com.desafio.application.generic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GenericResponse {

    private UUID id;

    @JsonProperty("created_at")
    private LocalDateTime createdDateTime;

    @JsonProperty("updated_at")
    private LocalDateTime updatedDateTime;

}
