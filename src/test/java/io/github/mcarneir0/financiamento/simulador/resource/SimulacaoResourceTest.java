package io.github.mcarneir0.financiamento.simulador.resource;

import io.github.mcarneir0.financiamento.simulador.dto.SimulacaoRequests;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@DisplayName("SimulacaoResource - Testes de Integração (API Rest)")
public class SimulacaoResourceTest {

    @Test
    @DisplayName("Deve criar uma simulação (POST) e buscá-la pelo ID (GET) com sucesso (Cenário Feliz)")
    void testCriarSimulacaoEbuscarPeloId() {
        BigDecimal valorInicial = BigDecimal.valueOf(1000);
        double taxaJurosMensal = 1.0;
        Integer prazoMeses = 12;

        // 1. Cria a simulação via POST
        int id = given()
            .contentType("application/json")
            .body(new SimulacaoRequests.CriarSimulacaoRequest(valorInicial, taxaJurosMensal, prazoMeses))
            .when()
            .post("/simulacoes")
            .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("valorTotalFinal", notNullValue())
            .body("valorTotalJuros", notNullValue())
            .body("parcelas", hasSize(12)) // Verifica se a API retornou a lista correta
            .extract().body().path("id");

        // 2. Busca a simulação gerada via GET
        given()
            .when()
            .get("/simulacoes/" + id)
            .then()
            .statusCode(200)
            .body("id", equalTo(id))
            // No JSON de retorno o valor BigDecimal pode vir interpretado como float/number pelo RestAssured
            .body("valorInicial", equalTo(1000.0f)) 
            .body("taxaJurosMensal", equalTo(1.0f))
            .body("prazoMeses", equalTo(12))
            .body("parcelas", hasSize(12));
    }

    @Test
    @DisplayName("Deve retornar 404 Not Found ao buscar ID inexistente (Erro/Borda)")
    void testBuscarSimulacaoInexistente() {
        given()
            .when()
            .get("/simulacoes/9999999")
            .then()
            .statusCode(404);
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request se valorInicial for menor que o mínimo permitido (Erro/Validação)")
    void testCriarSimulacaoValorInicialInvalido() {
        given()
            .contentType("application/json")
            .body(new SimulacaoRequests.CriarSimulacaoRequest(BigDecimal.ZERO, 1.0, 12)) // valor 0 não é permitido (@Min(1))
            .when()
            .post("/simulacoes")
            .then()
            .statusCode(400);
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request se prazoMeses for menor que o mínimo permitido (Erro/Validação)")
    void testCriarSimulacaoPrazoMesesInvalido() {
        given()
            .contentType("application/json")
            .body(new SimulacaoRequests.CriarSimulacaoRequest(BigDecimal.valueOf(1000), 1.0, 0)) // prazo 0 não é permitido (@Min(1))
            .when()
            .post("/simulacoes")
            .then()
            .statusCode(400);
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request se faltarem campos obrigatórios no payload (Erro/Validação)")
    void testCriarSimulacaoComCamposNulos() {
        given()
            .contentType("application/json")
            .body(new SimulacaoRequests.CriarSimulacaoRequest(null, 1.0, null)) 
            .when()
            .post("/simulacoes")
            .then()
            .statusCode(400);
    }

    @Test
    @DisplayName("Teste da instância da classe SimulacaoResource")
    void testSimulacaoResourceClass() {
        SimulacaoResource simulacaoResource = new SimulacaoResource();
        assertNotNull(simulacaoResource.toString());
    }
}
