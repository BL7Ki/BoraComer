package pos.java.bora_comer.infra.delivery.login.dto;

public record LoginResponseDTO(
        String token,
        String type // "Bearer"
) {
}