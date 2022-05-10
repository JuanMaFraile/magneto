package com.meli.jfraile.controller;

import com.meli.jfraile.bo.MagnetoBO;
import com.meli.jfraile.dto.MutantDTO;
import com.meli.jfraile.dto.StatsResponseDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import javax.validation.constraints.NotBlank;

@Controller()
public class MagnetoController {

    @Inject
    MagnetoBO magnetoBO;

    @Post("/mutant")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse isMutant (@Body @NotBlank MutantDTO body){
        boolean res = magnetoBO.validateMutant(body.getDna());
        return res ? HttpResponse.status(HttpStatus.OK) : HttpResponse.status(HttpStatus.FORBIDDEN);
    }

    @Get("/stats")
    @Produces(MediaType.APPLICATION_JSON)
    public StatsResponseDTO stats () {
        return magnetoBO.stats();
    }

}
