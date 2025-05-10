package pos.java.bora_comer.model;

import java.time.LocalDateTime;

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
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(name = "data_alteracao", nullable = false)
    private LocalDateTime dataUltimaAlteracao;

    @Embedded
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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
