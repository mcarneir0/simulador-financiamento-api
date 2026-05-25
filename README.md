# Simulador de Financiamento API

Bem-vindo ao **Simulador de Financiamento API**! Esta aplicação foi desenvolvida utilizando o framework **Quarkus** e fornece endpoints REST para calcular simulações de financiamentos usando juros compostos. Além do cálculo de totais, a API constrói o fluxo de pagamento detalhado (mês a mês) para que os usuários possam visualizar a evolução do saldo devedor e os juros aplicados em cada parcela.

A API foi construída seguindo boas práticas de desenvolvimento, com validações (`Hibernate Validator`), persistência em banco em memória (`H2` + `Panache`), ampla cobertura de testes unitários e de integração (`JUnit` + `RestAssured`), métricas de cobertura de código (`JaCoCo`) e documentação padronizada gerada automaticamente (`OpenAPI/Swagger`).

---

## 🚀 Como começar

O projeto utiliza o **Maven Wrapper** (`mvnw`), portanto, você não precisa ter o Maven instalado na sua máquina para executá-lo. Basta garantir que você tenha o Java (versão 25 ou superior) configurado.

Para Windows, utilize o comando `.\mvnw.cmd`. Para sistemas Linux/Mac, utilize `./mvnw`.

### 1. Compilar o projeto

Para compilar a aplicação e baixar todas as dependências, execute:

```bash
./mvnw clean compile
```

### 2. Executar em modo de desenvolvimento (Quarkus Dev Mode)

O Quarkus possui um excelente modo de desenvolvimento que permite *Live Reload* (alterações no código são refletidas em tempo real sem a necessidade de reiniciar o servidor). Para rodar a aplicação:

```bash
./mvnw quarkus:dev
```

A aplicação iniciará na porta `8080` por padrão. Para interromper o servidor, pressione `CTRL+C` no terminal.

### 3. Acessar a Documentação da API (Swagger UI)

Com a aplicação rodando (através do comando `quarkus:dev` ou do pacote final), você pode visualizar e interagir com todos os endpoints de forma visual usando o **Swagger UI**.

Acesse no seu navegador:
👉 **[http://localhost:8080/q/swagger-ui](http://localhost:8080/q/swagger-ui)**

Lá você encontrará os schemas de Requests e Responses bem como a possibilidade de enviar requisições de teste diretamente pela página.

---

## 🧪 Testes e Cobertura (JaCoCo)

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

*(Nota: a fase de `verify` cuida tanto do Surefire quanto do Failsafe, rodando e empacotando os testes da forma ideal para o Quarkus).*

### Relatório de Cobertura de Código

Sempre que a suíte de testes é executada, o **JaCoCo** compila um relatório em HTML avaliando linha a linha a cobertura do seu código. 

Para verificar o relatório após rodar o `verify`, basta abrir o seguinte arquivo no seu navegador preferido:

📁 **`target/jacoco-report/index.html`**

---

## 🛠️ Tecnologias Principais

- **[Quarkus](https://quarkus.io/)** - Framework Java Cloud Native
- **Hibernate ORM com Panache** - Simplificação da camada de acesso a dados
- **H2 Database** - Banco de dados em memória para agilidade no dev/testes
- **JUnit 5 & RestAssured** - Frameworks para testes unitários e de integração E2E
- **JaCoCo** - Ferramenta de Coverage para os testes
- **SmallRye OpenAPI** - Geração de documentação Swagger automática
