package io.github.mcarneir0.financiamento.simulador.resource;

import io.github.mcarneir0.financiamento.simulador.dto.SimulacaoRequests;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class SimulacaoResourceTest {

    @Test
    void testCriarSimulacaoEbuscarPeloId() {
        BigDecimal valorInicial = BigDecimal.valueOf(1000);
        double taxaJurosMensal = 1.0;
        Integer prazoMeses = 12;

        int id = given()
            .contentType("application/json")
            .body(new SimulacaoRequests.CriarSimulacaoRequest(valorInicial, taxaJurosMensal, prazoMeses))
            .when()
            .post("/simulacoes")
            .then()
            .statusCode(201)
            .extract().body().path("id");

        given()
            .when()
            .get("/simulacoes/" + id)
            .then()
            .statusCode(200);
    }

    @Test
    void testSimulacaoResourceClass() {
        SimulacaoResource simulacaoResource = new SimulacaoResource();
        assert simulacaoResource.toString() != null;
    }
}
