package br.com.desafio.application.generic;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class GenericPatchRequest {

    @NotNull
    private UUID id;

}
