# Simulador de Financiamentos API

Esta aplicação foi desenvolvida utilizando o framework **Quarkus** e fornece endpoints REST para calcular simulações de financiamentos usando juros compostos. Além do cálculo de totais, a API constrói o fluxo de pagamento detalhado (mês a mês) para que os usuários possam visualizar a evolução do saldo devedor e os juros aplicados em cada parcela.

A API foi construída seguindo boas práticas de desenvolvimento, com validações (`Hibernate Validator`), persistência em banco na memória (`H2` + `Panache`), ampla cobertura de testes unitários e de integração (`JUnit` + `RestAssured`), métricas de cobertura de código (`JaCoCo`) e documentação padronizada gerada automaticamente (`OpenAPI/Swagger`).

## 🚀 Como começar

O projeto utiliza o **Maven Wrapper** (`mvnw`), portanto, você não precisa ter o Maven instalado na sua máquina para executá-lo. Basta garantir que você tenha o Java (versão 25 ou superior) configurado. Para compilar em código nativo é necessário também ter instalado o **GraalVM**, você pode obter mais instruções sobre como configurá-lo [aqui](https://www.graalvm.org/latest/getting-started/).

Para Windows, utilize o comando `.\mvnw.cmd`. Para sistemas Linux/Mac, utilize `./mvnw`.

### 🧪 Testes e Cobertura (JaCoCo)

O projeto possui rigorosa cobertura contemplando cenários de borda (banco de dados vazio, entradas nulas) e fluxos de integração HTTP.

### Executar a suíte de testes

Para rodar todos os testes unitários e de integração, garantindo que o comportamento da aplicação está correto, execute o *goal* de verificação:

```bash
./mvnw clean verify
```

Ou

```bash
./mvnw clean verify -Pnative
```

### Relatório de Cobertura de Código

Sempre que a suíte de testes é executada, o **JaCoCo** compila um relatório em HTML avaliando linha a linha a cobertura do seu código. Para verificar o relatório, basta abrir o seguinte arquivo no seu navegador:

📁 **`target/jacoco-report/index.html`**

---

### ✨ Executar a aplicação

Após executar os testes é possível verificar os pacotes gerados pelo compilador do Maven. Eles ficam disponíveis na pasta abaixo:

📁 **`target/quarkus-app/quarkus-run.jar`**

Para iniciar o servidor, navegue até a pasta indicada e rode o comando:

```bash
java -jar ./quarkus-run.jar
```

A aplicação iniciará na porta `8080` por padrão. Para interromper o servidor, pressione `CTRL+C` no terminal.

### 📄 Acessar a Documentação da API (Swagger UI)

Com a aplicação rodando, você pode visualizar e interagir com todos os endpoints de forma visual usando o **Swagger UI**.

Acesse no seu navegador:
👉 **[http://localhost:8080/q/swagger-ui](http://localhost:8080/q/swagger-ui)**

Lá você encontrará os schemas de Requests e Responses bem como a possibilidade de enviar requisições de teste diretamente pela página.

## 🛠️ Tecnologias Principais

- **[Quarkus](https://quarkus.io/)** - Framework Java Cloud Native
- **Hibernate ORM com Panache** - Simplificação da camada de acesso a dados
- **H2 Database** - Banco de dados em memória para agilidade no dev/testes
- **JUnit 5 & RestAssured** - Frameworks para testes unitários e de integração E2E
- **JaCoCo** - Ferramenta de Coverage para os testes
- **SmallRye OpenAPI** - Geração de documentação Swagger automática
