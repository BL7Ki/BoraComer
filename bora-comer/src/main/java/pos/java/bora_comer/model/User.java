package pos.java.bora_comer.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id do usuario",
            example = "1",
            required = true)
    private Long id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Nome do usuario",
            example = "João da Silva",
            required = true)
    private String nome;

    @Column(nullable = false, unique = true, length = 100)
    @Schema(description = "Email do usuario",
            example = "exemplo@gmail.com",
            required = true)
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    @Schema(description = "Login do usuario",
            example = "joao.silva",
            required = true)
    private String login;

    @Column(nullable = false)
    @Schema(description = "Senha do usuario",
            example = "senha123",
            required = true)
    private String senha;

    @Column(name = "data_alteracao", nullable = false)
    @Schema(description = "Data da ultima alteração no usuario",
            example = "2023-10-01",
            required = true)
    private LocalDateTime dataUltimaAlteracao;

    @Embedded
    @Schema(description = "Endereço do usuario",
            required = true)
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Tipo de usuario (ADMIN, CLIENTE)")
    private UserRole role;

    // Construtor padrão (necessário pro JPA)
    public User() {
        this.dataUltimaAlteracao = LocalDateTime.now();
    }

    // Construtor completo
    public User(String nome, String email, String login, String senha, Endereco endereco, UserRole role) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.endereco = endereco;
        this.role = role;
        this.dataUltimaAlteracao = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDateTime getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public UserRole getRole() {
        return role;
    }

    // Setters apenas para campos alteráveis
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    // Atualizar data de alteração
    public void atualizarDataUltimaAlteracao() {
        this.dataUltimaAlteracao = LocalDateTime.now();
    }
}
