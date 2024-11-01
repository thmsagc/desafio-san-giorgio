package br.com.desafio.presentation.pageable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class PageableParams {

    @Min(0)
    private int page = 0;

    @Min(1)
    @Max(100)
    private int size = 100;

    @Pattern(regexp = "(?i)^[a-z0-9_]+(?:,(asc|desc))?$")
    private List<String> sort;

}
