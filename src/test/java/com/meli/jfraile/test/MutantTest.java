package com.meli.jfraile.test;

import com.meli.jfraile.dto.MutantDTO;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
public class MutantTest {

    //@Inject ApplicationC

    @Inject
    @Client("${micronaut.application.base_path}")
    HttpClient client;

    @Test
    void testNoMutante () {
        try {
            MutantDTO dto = new MutantDTO();
            String[] lines = {"ACTGTT", "ATTGGC", "AGCCTT", "GTCAGC", "TCAGTA", "ACAGTA"};
            dto.setDna(lines);
            HttpRequest request = HttpRequest.POST("mutant", dto);
            client.toBlocking().exchange(request, JsonNode.class);
        } catch(HttpClientResponseException ex){
            Assertions.assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
        }
    }

    @Test
    void testMutante1 () {
        MutantDTO dto = new MutantDTO();
        String[] lines = {"ACTGACT", "ACTGCAT", "GCGATTG", "AGATCGA", "GCTTTAA", "CTATTTT", "AGTCAGA"};
        dto.setDna(lines);
        HttpRequest request = HttpRequest.POST("mutant", dto);
        var response = client.toBlocking().exchange(request, JsonNode.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void testMutante2 () {
        MutantDTO dto = new MutantDTO();
        String[] lines = {"ACTGAC", "TGACTC", "CTAGCA", "CTGCAG", "AGGACT", "GCATAC"};
        dto.setDna(lines);
        HttpRequest request = HttpRequest.POST("mutant", dto);
        var response = client.toBlocking().exchange(request, JsonNode.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void testStats () {
        HttpRequest request = HttpRequest.GET("stats");
        var response = client.toBlocking().exchange(request, JsonNode.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
    }

}
