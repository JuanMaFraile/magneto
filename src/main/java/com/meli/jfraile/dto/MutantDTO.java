package com.meli.jfraile.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Introspected
public class MutantDTO {

    @NotBlank @NotNull String[] dna;

}
