# Projeto: Bora Comer

## Equipe
- Nome do Aluno 1 - RM12345
- Nome do Aluno 2 - RM67890

---

## 1. Introdução

**Descrição do problema**  
O projeto "Bora Comer" visa atender à necessidade de um sistema de gestão para restaurantes, permitindo o gerenciamento eficiente de usuários, cardápios e pedidos.

**Objetivo do projeto**  
Desenvolver um backend robusto utilizando **Spring Boot** para gerenciar usuários e atender aos requisitos definidos, seguindo boas práticas de desenvolvimento e princípios de design como **SOLID** e **Arquitetura Hexagonal**.

---

## 2. Arquitetura do Sistema

**Descrição da Arquitetura**  
O sistema foi desenvolvido utilizando a **Arquitetura Hexagonal**, promovendo a separação de responsabilidades e facilitando a manutenção e evolução do código.

## Estrutura do Projeto
O projeto segue a **Arquitetura Hexagonal**, separando as responsabilidades em camadas bem definidas:

### 1. **Camada de Domínio**
- Contém as classes centrais do domínio, como `UserDomain` e `AddressDomain`.
- Representa o núcleo da aplicação, independente de frameworks ou tecnologias externas.

### 2. **Camada de Casos de Uso**
- Define os contratos (interfaces) e implementações para os casos de uso, como `CreateUserUseCase` e `SearchUserUseCase`.
- Contém a lógica de negócio e orquestra as interações entre o domínio e as portas externas.

### 3. **Camada de Infraestrutura**
- Implementa as portas externas, como controladores REST (`CreateUserController`) e mapeadores (`UserMapper`).
- Faz a integração com frameworks e tecnologias externas.

### Mappers
Os mappers são responsáveis por converter objetos entre diferentes camadas da aplicação, garantindo que os dados sejam transformados corretamente.
- **Exemplo**: O `UserMapper` converte um `UserRequestDTO` recebido na camada de entrega (infra/delivery) para um `User` utilizado na camada de domínio, e vice-versa.

Essa abordagem promove a separação de responsabilidades e mantém as camadas desacopladas.

### 4. **Camada de Persistência**
- Contém as entidades mapeadas para persistência, como `UserEntity`, e os repositórios que interagem com o banco de dados.
- Utiliza o Spring Data JPA para facilitar a persistência e recuperação de dados.
- Os repositórios são interfaces que estendem `JpaRepository`, permitindo operações CRUD sem a necessidade de implementação manual.
- **Exemplo**: O `UserRepository` é uma interface que estende `JpaRepository<UserEntity, Long>`, permitindo operações como salvar, buscar e deletar usuários.
- As entidades de persistência são mapeadas para o banco de dados, utilizando anotações do JPA, como `@Entity`, `@Table`, `@Column`, etc.
- **Exemplo**: A classe `UserEntity` é anotada com `@Entity` e possui os atributos `id`, `name`, `email`, etc., mapeados para as colunas da tabela correspondente no banco de dados.

---

## 3. Princípios SOLID Aplicados
### **DIP (Dependency Inversion Principle)**
- As classes dependem de abstrações (interfaces) em vez de implementações concretas.
- Exemplos:
  - `CreateUserController` depende da interface `CreateUserUseCase`, e não de sua implementação concreta.
  - `CreateUserUseCaseImpl` utiliza a abstração `UserMapper` para realizar o mapeamento de objetos.

---

## 4. Tecnologias Utilizadas
- **Java 21**: Linguagem principal do projeto.
- **Spring Boot 3.4.4**: Framework para desenvolvimento do backend.
- **Banco de dados H2 (em memória)**: Para testes e desenvolvimento rápido.
- **Docker**: Para containerização e fácil deploy.

---

## 5. Pré-requisitos
- **Java 21** ou superior instalado.
- **Maven** para gerenciamento de dependências.
- **Docker** (opcional, para rodar o banco de dados em contêiner).

---

## 6. Instalação e Execução

### Clonar o repositório
```bash
git clone https://github.com/seu-usuario/bora-comer.git
cd bora-comer
```

### Configurar o banco de dados
- Configure o arquivo `application.properties` para apontar para o banco de dados MySQL ou utilize o H2 para testes.

### Rodar o projeto
```bash
mvn spring-boot:run
```

### Rodar com Docker Compose

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
   Ou, se estiver em outra máquina na rede:
   ```
   http://<ip-da-maquina>:8080
   ```

### Acessar a aplicação
- A API estará disponível em: `http://localhost:8080`

---

## 7. Exemplos de Uso

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

## 8. Testes
Para rodar os testes automatizados:
```bash
mvn test
```

---

## 9. Contribuição
Contribuições são bem-vindas! Para contribuir:
1. Faça um fork do repositório.
2. Crie uma branch para sua feature (`git checkout -b minha-feature`).
3. Faça commit das suas alterações (`git commit -m 'Minha nova feature'`).
4. Envie um push para a branch (`git push origin minha-feature`).
5. Abra um Pull Request.

---

## 10. Estrutura de Pastas

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

# 11. Acessar a Documentação da API (Swagger UI)

Acesse essa URL após iniciar a aplicação para visualizar os endpoints disponíveis e testar as requisições diretamente pela interface do Swagger.
```
http://localhost:8080/swagger-ui/index.html
```