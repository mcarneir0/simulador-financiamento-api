package io.github.mcarneir0.financiamento.simulador;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Simulador de Financiamentos - API",
                version = "1.0.0",
                contact = @Contact(
                        name = "Matheus Carneiro",
                        email = "mathheus.feitosa@caixa.gov.br",
                        url = "https://mcarneir0.github.io/"
                ),
                description = "API para simular financiamentos - Caixaverso"
        ))
public class Swagger extends Application {
    // Esta classe é necessária para que o Quarkus reconheça as anotações do OpenAPI
    // e gere a documentação Swagger automaticamente.
}
