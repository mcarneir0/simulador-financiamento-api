package io.github.mcarneir0.financiamento.simulador;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class SwaggerTest {

    @Test
    void testSwagger() {
        Swagger swagger = new Swagger();
        assertNotNull(swagger, "A classe Swagger deve ser instanciada corretamente");
    }
}
