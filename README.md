# Projeto: Bora Comer

## 1. Equipe
- Leonardo Felipe Ventura Ferreira - RM363339
- Wagner de Lima Braga Silva - RM364223
- Everton Cristiano de Souza Teixeira - RM362065

## 1.1 Link Repositorio:
branch: Main
```
https://github.com/BL7Ki/BoraComer

```

## 2. Introdução

**Descrição do problema**  
O projeto "Bora Comer" visa atender à necessidade de um sistema de gestão para restaurantes, permitindo o gerenciamento eficiente de usuários, cardápios e pedidos.

**Objetivo do projeto**  
Desenvolver um backend robusto utilizando **Spring Boot** para gerenciar usuários e atender aos requisitos definidos, seguindo boas práticas de desenvolvimento e princípios de design como **SOLID**.

---

## 3. Tecnologias Utilizadas
- **Java 21**
- **Spring Boot 3.4.4**
- **Banco de dados H2 (em memória)**
- **Docker**
- **Maven**

---

## 4. Pré-requisitos
- **Java 21** ou superior instalado.
- **Maven**
- **Docker** (opcional)

---

## 5. Instalação e Execução

### 5.1 Clonar o repositório
```bash
git clone https://github.com/BL7Ki/BoraComer.git
cd bora-comer

```
### 5.2 Configurar o banco de dados
- Para o H2, não é necessário configuração adicional, pois ele roda em memória.
- Nesta aplicação, o Hibernate está configurado para **não criar, alterar ou validar o esquema do banco de dados automaticamente**. Isso significa que o banco de dados deve ser configurado manualmente utilizando scripts SQL, como `data.sql`.
- spring.jpa.hibernate.ddl-auto: none: Desativa a criação ou alteração automática do esquema pelo Hibernate.
- spring.sql.init.mode: always: Garante que os scripts SQL (data.sql) sejam executados ao iniciar a aplicação.
#### Configuração no `application-test.yml`:
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: none
  sql:
    init:
      mode: always
      
```

### 5.3 Rodar o projeto
```bash
mvn spring-boot:run
```
### 5.4 Rodar com Docker Compose
1. Gere o arquivo JAR da aplicação:
   ```bash
   mvn clean package
   ```
   O arquivo será gerado em `./target`.
2. Edite o arquivo `docker-compose.yml` e ajuste o nome do JAR no serviço `app` se necessário.
3. Suba os containers:
   ```bash
   docker-compose up --build
   ```
4. Acesse a aplicação pelo navegador ou ferramenta de requisições:
   ```
    http://localhost:8080
    ```
## 6. Estrutura de Pastas

````
collection/                              # Coleção do Insomnia para testes arquivos json requisições
src/
├── main/
│   ├── java/
│   │   ├── br/com/sistemalima/app/
│   │   │   ├── modelo/
│   │   │   │   ├── config/              # Configurações adicionais
│   │   │   │   ├── core/                # Camada de domínio e casos de uso
│   │   │   │   │   ├── domain/          # Entidades de domínio
│   │   │   │   │   ├── errors/          # Exceções e erros personalizados
│   │   │   │   │   ├── gateway/         # Interfaces de gateway responsável por abstraem a comunicação entre os casos de uso (use cases) e a camada de infraestrutura.
│   │   │   │   │   ├── usercase/        # Interfaces e implementações dos casos de uso
│   │   │   │   │   │   ├── user         # Exemplo de caso de uso usuario
│   │   │   │   │   │   │   ├── impl/    # Implementações dos casos de uso usuario
│   │   │   │   │   │   ├── login/       # Exemplo de caso de uso login
│   │   │   │   │   │   │   ├── impl/    # Implementações dos casos de uso login
│   │   │   │   │   ├── mapper/          # Mapeadores de domínio
│   │   │   │   ├── infra/               # Camada de infraestrutura
│   │   │   │   │   ├── delivery/        # Controladores e mapeadores de DTOs
│   │   │   │   │   ├── gateway/         # Implementações de gateways (persistência e APIs externas)   
│   │   │   │   │   ├── persistence/     # Persistência
│   │   │   │   │   │   ├── entity/      # Entidades mapeadas para persistência
│   │   │   │   │   │   ├── mapper/      # Mapeadores para conversão entre entidades de persistência e objetos de domínio
│   │   │   │   │   │   ├── repository/  # Interfaces e implementações de repositórios
│   │   │   │   ├── application/         # Configurações da aplicação
````

``

## 7. Arquitetura do Sistema

**Descrição da Arquitetura**  
O sistema foi desenvolvido utilizando a **Clean Architecture (Arquitetura Limpa)**, promovendo a separação de responsabilidades e facilitando a manutenção e evolução do código.


### 7.1 **Diagrama da Arquitetura**
```
+------------------------------------------------------------+
|                        Camada de Infraestrutura            |
|               (Controllers REST, Mappers, Frameworks)      |
+-----------------------------+------------------------------+
                              |
                              v
+----------------+     +---------------------+     +-------------------+
|  Camada de     |<--->|  Camada de Casos    |<--->|  Camada de        |
|  Persistência  |     |  de Uso (UseCases)  |     |  Domínio          |
| (Entities,     |     | (Interfaces e Impl) |     | (Entidades,       |
|  Repositórios) |     +---------------------+     |  Regras de Negócio|
+----------------+                               +----------------------+
                              ^
                              |
+-----------------------------+------------------------------+
|                        Camada de Gateway                  |
|       (Abstrações para APIs externas e persistência)      |
+------------------------------------------------------------+

```
---

### Legenda:

* Camada de Infraestrutura: Controllers REST, mapeadores de DTOs, integração com frameworks.

* Camada de Casos de Uso: Interfaces e implementações dos casos de uso.

* Camada de Domínio: Entidades centrais e regras de negócio.

* Camada de Persistência: Entidades JPA e repositórios.

* Camada de Gateway: Interfaces que abstraem a comunicação entre os casos de uso e a infraestrutura, como persistência e APIs externas.

* Fluxo:
  Usuário → Controller (Infra) → UseCase (Aplicação) → Domínio → Gateway → Persistência (Repository/Entity)
  E vice-versa para respostas.

### 8. Princípios SOLID Aplicados
### **DIP (Dependency Inversion Principle)**
- As classes dependem de abstrações (interfaces) em vez de implementações concretas.
- Exemplos:
  - `CreateUserController` depende da interface `CreateUserUseCase`, e não de sua implementação concreta.
  - `CreateUserUseCaseImpl` utiliza a abstração `UserMapper` para realizar o mapeamento de objetos.

### 1. **Camada de Domínio**
- Contém as classes centrais do domínio, como `User` e `Address`.
- Representa o núcleo da aplicação, independente de frameworks ou tecnologias externas.

### 2. **Camada de Casos de Uso**
- Define os contratos (interfaces) e implementações para os casos de uso, como `CreateUserUseCase` e `SearchUserUseCase`.
- Centraliza a lógica de aplicação, orquestrando as regras de negócio do domínio e coordenando as interações entre entidades e serviços externos.
- Depende apenas das entidades do domínio, mantendo-se independente de frameworks, detalhes de infraestrutura ou tecnologia.
- Garante que as regras de negócio da aplicação estejam isoladas e facilmente testáveis.

### 3. **Camada de Infraestrutura**
- Responsável por implementar os mecanismos de entrada e saída do sistema, como controladores REST, gateways, serviços externos e mapeadores.
- Realiza a integração com frameworks, bibliotecas e tecnologias externas (ex: Spring Boot, bancos de dados, APIs externas).
- Adapta as requisições externas para o formato esperado pelos casos de uso e domínio, mantendo o núcleo da aplicação isolado de detalhes de infraestrutura.

### 4. Mappers
Os mappers são responsáveis por converter objetos entre diferentes camadas da aplicação, garantindo que os dados sejam transformados corretamente.
- **Exemplo**: O `UserMapper` converte um `UserRequestDTO` recebido na camada de entrega (infra/delivery) para um `User` utilizado na camada de domínio, e vice-versa.

Essa abordagem promove a separação de responsabilidades e mantém as camadas desacopladas.

### 5. **Camada de Persistência**
- Contém as entidades mapeadas para persistência, como `UserEntity`, e os repositórios que interagem com o banco de dados.
- Utiliza o Spring Data JPA para facilitar a persistência e recuperação de dados.
- Os repositórios são interfaces que estendem `JpaRepository`, permitindo operações CRUD sem a necessidade de implementação manual.
- **Exemplo**: O `UserRepository` é uma interface que estende `JpaRepository<UserEntity, Long>`, permitindo operações como salvar, buscar e deletar usuários.
- As entidades de persistência são mapeadas para o banco de dados, utilizando anotações do JPA, como `@Entity`, `@Table`, `@Column`, etc.
- **Exemplo**: A classe `UserEntity` é anotada com `@Entity` e possui os atributos `id`, `name`, `email`, etc., mapeados para as colunas da tabela correspondente no banco de dados.

--- 

### 9. Endpoints da API
| **Endpoint**                  | **Método** | **Descrição**                                 |
|-------------------------------|------------|-----------------------------------------------|
| `/users`                      | `POST`     | Criar um novo usuário.                        |
| `/users/{id}`                 | `GET`      | Buscar um usuário por ID.                     |
| `/users`                      | `GET`      | Buscar todos os usuários com paginação.       |
| `/users/{id}`                 | `PUT`      | Atualizar um usuário por ID.                  |
| `/users/{id}`                 | `DELETE`   | Deletar um usuário por ID.                    |
| `/users/{id}/change-password` | `PUT`      | Trocar a senha do usuário.                    |
| `/login`                      | `POST`     | Validar o login do usuário.                   |

---
## 10. Exemplos de Uso

### Trocar senha do usuário

**Requisição:**
```bash
PUT /users/{id}/change-password
Content-Type: application/json

{
  "currentPassword": "senhaAntiga",
  "newPassword": "senhaNova"
}

```

### Resposta de sucesso:
```json
{
  "message": "Senha alterada com sucesso."
}
```

**Resposta:**
```json
{
  "status": 400,
  "error": "BAD_REQUEST",
  "message": "Senha atual incorreta.",
  "path": "/users/1/change-password"
}
```


### Criar um usuário
**Requisição:**
```bash
POST /users
Content-Type: application/json

{
  "nome": "Joao Silva ",
  "email": "joao.silva@example.com",
  "login": "tiagosilva",
  "senha": "senha123",
  "endereco": {
    "rua": "Rua das Flores doces",
    "bairro": "Bairro das cascatas pretas",
    "cidade": "Minas",
    "estado": "PE",
    "cep": "12345-400"
  },
  "papel": "ADMIN",
	"tipo_usuario": {
  	"name": "CLIENTE"
		}
}
```

**Resposta:**
```json
{
	"id": 2,
	"nome": "Joao Silva ",
	"email": "joao.silva@example.com",
	"nome_usuario": "tiagosilva",
	"endereco": {
		"rua": "Rua das Flores doces",
		"bairro": "Bairro das cascatas pretas",
		"cidade": "Minas",
		"estado": "PE",
		"cep": "12345-400"
	},
	"papel": "ADMIN",
	"data_criacao": "2025-07-13T15:17:20.601449100",
	"data_alteracao": null,
	"tipo_usuario": "CLIENTE"
}
```

### Validar Login
O endpoint `/login` permite validar as credenciais de um usuário. Ele verifica o login e a senha fornecidos e retorna uma mensagem indicando o sucesso ou falha da autenticação.

**Requisição:**
```bash
POST /login
Content-Type: application/json

{
  "login": "admin",
  "password": "senhaAdmin123"
}

```

**Resposta:**
Login bem-sucedido (HTTP 200):
```json
{
  "message": "Login realizado com sucesso."
}

 ```
Credenciais inválidas (HTTP 401):
```json
{
  "message": "Credenciais inválidas."
}
```

---

### Acessar a aplicação
- A API estará disponível em: `http://localhost:8080`

---

## 11. Testes
Para rodar os testes automatizados:
```bash
mvn test
```

---

## 12. Contribuição
Contribuições são bem-vindas! Para contribuir:
1. Faça um fork do repositório.
2. Crie uma branch para sua feature (`git checkout -b minha-feature`).
3. Faça commit das suas alterações (`git commit -m 'Minha nova feature'`).
4. Envie um push para a branch (`git push origin minha-feature`).
5. Abra um Pull Request.

---

## 13. Fase 2: Expansão do Sistema

Essa fase expande o sistema ao incluir a gestão dos tipos de usuários, cadastro de restaurantes e cardápios, reforçando práticas de desenvolvimento e estruturação de código limpo.

### 13.1 Tipo de Usuário

Implementa uma estrutura para distinguir entre usuários "Dono de Restaurante" e "Cliente", incluindo um CRUD para gerenciar tipos de usuário e associá-los a usuários existentes.

- **Campos necessários para o cadastro de tipo usuário:**
    - Nome do Tipo

**Observação:**  
Foi criada uma associação entre o usuário e o tipo de usuário, permitindo identificar o perfil de cada usuário no sistema.

### 13.2 Cadastro de Restaurante

CRUD completo para cadastro de restaurantes, permitindo associar um usuário como dono do restaurante.

- **Campos necessários para o cadastro de restaurante:**
    - Nome
    - Endereço
    - Tipo de cozinha
    - Horário de funcionamento
    - Dono do restaurante (usuário responsável)

### 13.3 Cadastro dos Itens do Cardápio

CRUD para os itens vendidos no restaurante, com detalhes completos.

- **Campos necessários para o cadastro de um item do cardápio:**
    - Nome
    - Descrição
    - Preço
    - Disponibilidade para pedir apenas no restaurante
    - Foto do prato (armazenado como caminho da imagem)

---

## 14. Endpoints Adicionados na Fase 2

| **Endpoint**                        | **Método** | **Descrição**                                      |
|--------------------------------------|------------|----------------------------------------------------|
| `/user-types`                       | `POST`     | Criar um novo tipo de usuário                      |
| `/user-types/{id}`                  | `GET`      | Buscar tipo de usuário por ID                      |
| `/user-types`                       | `GET`      | Listar todos os tipos de usuário                   |
| `/user-types/{id}`                  | `PUT`      | Atualizar tipo de usuário                          |
| `/user-types/{id}`                  | `DELETE`   | Deletar tipo de usuário                            |
| `/restaurants`                      | `POST`     | Criar um novo restaurante                          |
| `/restaurants/{id}`                 | `GET`      | Buscar restaurante por ID                          |
| `/restaurants`                      | `GET`      | Listar todos os restaurantes                       |
| `/restaurants/{id}`                 | `PUT`      | Atualizar restaurante                              |
| `/restaurants/{id}`                 | `DELETE`   | Deletar restaurante                                |
| `/menu-items`                       | `POST`     | Criar um novo item de cardápio                     |
| `/menu-items/{id}`                  | `GET`      | Buscar item de cardápio por ID                     |
| `/menu-items`                       | `GET`      | Listar todos os itens de cardápio                  |
| `/menu-items/{id}`                  | `PUT`      | Atualizar item de cardápio                         |
| `/menu-items/{id}`                  | `DELETE`   | Deletar item de cardápio                           |

---

## 15. Exemplos de Uso dos Novos Endpoints

### Criar Tipo de Usuário

**Requisição:**
```json
POST /user-types
Content-Type: application/json

{
  "name": "Dono de Restaurante"

}
```
### Criar Restaurante
**Requisição:**
```json
POST /restaurants
Content-Type: application/json

{
"nome": "Restaurante Saboroso",
"endereco": "Rua das Flores, 123",
"tipoCozinha": "Japonesa",
"horarioFuncionamento": "10:00-22:00",
"donoId": 1
}

```

### Criar Item do Cardápio
**Requisição:**

```json
POST /menu-items
Content-Type: application/json

{
  "nome": "Temaki de Salmão",
  "descricao": "Temaki recheado com salmão fresco e cebolinha",
  "preco": 24.90,
  "soNoLocal": false,
  "imagemCaminho": "/imagens/temaki.jpg",
  "restauranteId": 1
}

```

### Acessar swagger
- Acesse a documentação Swagger da API em: `http://localhost:8080/swagger-ui.html`
- A documentação Swagger fornece uma interface interativa para explorar os endpoints da API, facilitando o teste e a compreensão das funcionalidades disponíveis.


## 16. Collection do Insomnia

Para facilitar os testes das requisições da API, incluímos uma collection do Insomnia no projeto.

**Localização do arquivo:**  
`collection/bora_comer_collection_insomnia.json`

**Como importar:**
1. Abra o Insomnia.
2. Clique em **Application** > **Import/Export** > **Import Data** > **From File**.
3. Selecione o arquivo `bora_comer_collection_insomnia.json` localizado na pasta `collection` do projeto.
4. A collection será importada com os endpoints configurados para facilitar os testes.

**Endpoints disponíveis na collection:**
- Criar usuário (`POST /users`)
- Buscar usuário por ID (`GET /users/{id}`)
- Atualizar usuário (`PUT /users/{id}`)
- Deletar usuário (`DELETE /users/{id}`)
- Listar todos os usuários (`GET /users`)
- Trocar senha do usuário (`PUT /users/{id}/change-password`)
- Validar login (`POST /login`)
- Criar tipo de usuário (`POST /user-types`)
- Buscar tipo de usuário por ID (`GET /user-types/{id}`)
- Listar todos os tipos de usuário (`GET /user-types`)
- Atualizar tipo de usuário (`PUT /user-types/{id}`)
- Deletar tipo de usuário (`DELETE /user-types/{id}`)
- Criar restaurante (`POST /restaurants`)
- Buscar restaurante por ID (`GET /restaurants/{id}`)
- Listar todos os restaurantes (`GET /restaurants`)
- Atualizar restaurante (`PUT /restaurants/{id}`)
- Deletar restaurante (`DELETE /restaurants/{id}`)
- Criar item de cardápio (`POST /menu-items`)
- Buscar item de cardápio por ID (`GET /menu-items/{id}`)
- Listar todos os itens de cardápio (`GET /menu-items`)
- Atualizar item de cardápio (`PUT /menu-items/{id}`)
- Deletar item de cardápio (`DELETE /menu-items/{id}`)

## 17. Video de Demonstração apresentando as funcionalidades solicitadas e o projeto executando e funcionando.

*  Link do vídeo:
   https://www.youtube.com/watch?v=Xphdvojwo5M&feature=youtu.be

## 18 Fase 3: Evolução do Sistema

### 18.1 Requisitos e Funcionalidades

**Funcionalidades obrigatórias:**
- Autenticação e autorização com Spring Security e JWT (expiração de 6 horas).
- Perfis (roles): `ADMIN` (gerente) e `CLIENT` (cliente).
- CRUD de Usuário (apenas gerente).
- CRUD de Restaurante (apenas gerente).
- CRUD de Cardápio (apenas gerente).
- Reservas de mesas com verificação de conflitos de horário
- Cliente solicita reserva; gerente lista/gerencia reservas.
- Pedidos (delivery e presencial) com atualização de status e listagem por usuário
- Cliente solicita pedido; gerente lista/atualiza pedidos.
- Acesso controlado por perfis (roles) em todos os endpoints.
- Notificações dos pedidos criados e finalizados.

### 18.2 Comunicação Assíncrona

- Mensageria via RabbitMQ:
    - Eventos: pedido criado, pedido finalizado.
    - O `notification-service` consome esses eventos e envia lembretes (mock em log/console).
    - Configuração via Docker Compose.

### 18.3 GraphQL

- Serviço GraphQL para consultas flexíveis:
    - Histórico de pedidos (com filtros).
    - Reservas futuras e passadas.
    - Consulta de cardápio.


### 18.4 Endpoints Adicionais

- Reservas de mesas:
    - `/reserves` (POST, GET, PUT, DELETE)
- Pedidos:
    - `/orders` (POST, GET, PUT, DELETE)
- Notificações (via mensageria, não exposto como endpoint REST)

- Itens do Pedido:
    - `/order-items` (POST, GET, PUT, DELETE)

### 18.5 Authenticação e Autorização
- Implementação de JWT para autenticação.
- Controle de acesso baseado em perfis (roles).
- endpoints protegidos conforme o perfil do usuário.
- header Authorization
---