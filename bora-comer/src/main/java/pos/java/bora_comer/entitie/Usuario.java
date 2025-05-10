package pos.java.bora_comer.entitie;

import java.time.LocalDate;

public class Usuario {
    private String nome;
    private String email;
    private String login;
    private String senha;
    private String telefone;
    private LocalDate dataUltAlteracao;
    private String endereco;

    public Usuario(String nome, String email, String login, String senha, String telefone, LocalDate dataUltAlteracao, String endereco) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.telefone = telefone;
        this.dataUltAlteracao = dataUltAlteracao;
        this.endereco = endereco;
    }

}
