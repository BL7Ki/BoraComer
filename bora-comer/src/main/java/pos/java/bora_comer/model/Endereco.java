package pos.java.bora_comer.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco {

    @Schema(description = "Rua do endereço",
            example = "Rua das Flores",
            required = true)
    private String rua;
    @Schema(description = "Bairro do endereço",
            example = "Centro",
            required = true)
    private String bairro;
    @Schema(description = "Cidade do endereço",
            example = "São Paulo",
            required = true)
    private String cidade;
    @Schema(description = "Estado do endereço",
            example = "SP",
            required = true)
    private String estado;
    @Schema(description = "CEP do endereço",
            example = "12345-678",
            required = true)
    private String cep;

    // Getters e Setters
    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }
    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}

