package com.meli.jfraile.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Introspected
public class StatsResponseDTO {

    @JsonProperty("count_mutant_dna")
    Integer countMutantDna;

    @JsonProperty("count_human_dna")
    Integer countHumanDna;

    @JsonProperty("ratio")
    Double ratio;


}
