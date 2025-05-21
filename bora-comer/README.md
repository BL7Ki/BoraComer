# Projeto: Bora Comer

## 1. Equipe
- Leonardo Felipe Ventura Ferreira - rm363339
-  Everton Cristiano de Souza Teixeira -
-  Wagner de Lima Braga Silva - RM364223
-  Suelen dos Santos Peres -
-  Gabriel Oliveira -

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
src/
├── main/
│   ├── java/
│   │   ├── br/com/sistemalima/app/
│   │   │   ├── modelo/
│   │   │   │   ├── core/                # Camada de domínio e casos de uso
│   │   │   │   │   ├── domain/          # Entidades de domínio
│   │   │   │   │   ├── usercase/        # Interfaces e implementações dos casos de uso
│   │   │   │   │   │   ├── impl/        # Implementações dos casos de uso
│   │   │   │   │   ├── mapper/          # Mapeadores de domínio
│   │   │   │   ├── infra/               # Camada de infraestrutura
│   │   │   │   │   ├── delivery/        # Controladores e mapeadores de DTOs
│   │   │   │   │   │   
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
                |        Camada de            |
                |      Infraestrutura         |
                | (Controllers REST, Mappers) |
                +-------------+---------------+
                              |
                              v
+----------------+     +---------------------+     +-------------------+
|  Camada de     |<--->|  Camada de Casos    |<--->|  Camada de        |
|  Persistência  |     |  de Uso (UseCases)  |     |  Domínio          |
| (Entities,     |     | (Interfaces e Impl) |     | (Entidades,       |
|  Repositórios) |     +---------------------+     |  regras de negócio|
+----------------+                               +----------------------+

```
---

### Legenda:

* Camada de Infraestrutura: Controllers REST, mapeadores de DTOs, integração com frameworks.

* Camada de Casos de Uso: Interfaces e implementações dos casos de uso.

* Camada de Domínio: Entidades centrais e regras de negócio.

* Camada de Persistência: Entidades JPA e repositórios.
* Fluxo:
  Usuário → Controller (Infra) → UseCase (Aplicação) → Domínio → Persistência (Repository/Entity)
  E vice-versa para respostas.

### 8. Princípios SOLID Aplicados
### **DIP (Dependency Inversion Principle)**
- As classes dependem de abstrações (interfaces) em vez de implementações concretas.
- Exemplos:
  - `CreateUserController` depende da interface `CreateUserUseCase`, e não de sua implementação concreta.
  - `CreateUserUseCaseImpl` utiliza a abstração `UserMapper` para realizar o mapeamento de objetos.

### 1. **Camada de Domínio**
- Contém as classes centrais do domínio, como `UserDomain` e `AddressDomain`.
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
| **Endpoint**       | **Método** | **Descrição**                          |
|---------------------|------------|----------------------------------------|
| `/users`            | `POST`     | Criar um novo usuário.                 |
| `/users/{id}`       | `GET`      | Buscar um usuário por ID.              |
| `/users`            | `GET`      | Buscar todos os usuários com paginação.|
| `/users/{id}`       | `PUT`      | Atualizar um usuário por ID.           |
| `/users/{id}`       | `DELETE`   | Deletar um usuário por ID.             |


## 10. Exemplos de Uso

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
  "tipo_usuario": "ADMIN"
}
```

**Resposta:**
```json
{
  "id": 1,
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
  "tipo_usuario": "ADMIN",
  "data_alteracao": "2025-05-12T13:21:47.537211300"
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