package pos.java.bora_comer.core.errors;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomExceptionHandlerTest {

    private CustomExceptionHandler exceptionHandler;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        exceptionHandler = new CustomExceptionHandler();
        request = mock(HttpServletRequest.class);
        when(request.getServletPath()).thenReturn("/api/test");
        when(request.getRequestURI()).thenReturn("/api/test");
    }

    @Test
    void deveTratarSummerException() {
        SummerException ex = new SummerException("Erro Summer");

        ErroView erro = exceptionHandler.SummerException(ex, request);

        assertEquals("Erro Summer", erro.getMessage());
        assertEquals("/api/test", erro.getPath());
        assertEquals(400, erro.getStatus());
        assertEquals("BAD_REQUEST", erro.getError());
        assertNotNull(erro.getTimestamp());
    }

    @Test
    void deveTratarUserDomainException() {
        UserDomainException ex = new UserDomainException("Erro de domínio");

        ErroView erro = exceptionHandler.SummerParseException(ex, request);

        assertEquals("Erro de domínio", erro.getMessage());
        assertEquals("/api/test", erro.getPath());
        assertEquals(400, erro.getStatus());
        assertEquals("BAD_REQUEST", erro.getError());
        assertNotNull(erro.getTimestamp());
    }

    @Test
    void deveTratarSummerNotFoundException() {
        SummerNotFoundException ex = new SummerNotFoundException("Não encontrado");

        ErroView erro = exceptionHandler.SummerNotFoundException(ex, request);

        assertEquals("Não encontrado", erro.getMessage());
        assertEquals("/api/test", erro.getPath());
        assertEquals(404, erro.getStatus());
        assertEquals("NOT_FOUND", erro.getError());
        assertNotNull(erro.getTimestamp());
    }

    @Test
    void deveTratarGenericException() {
        Exception ex = new Exception("Erro genérico");

        ErroView erro = exceptionHandler.handleGenericException(ex, request);

        assertEquals("Erro genérico", erro.getMessage());
        assertEquals("/api/test", erro.getPath());
        assertEquals(500, erro.getStatus());
        assertEquals("INTERNAL_SERVER_ERROR", erro.getError());
        assertNotNull(erro.getTimestamp());
    }

    @Test
    void deveTratarMethodArgumentNotValidException() {
        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "objectName");
        bindingResult.addError(new FieldError("objectName", "campo1", "não pode ser nulo"));
        bindingResult.addError(new FieldError("objectName", "campo2", "inválido"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ErroView erro = exceptionHandler.handleValidationExceptions(ex, request);

        String mensagemErro = erro.getMessage();
        assertTrue(mensagemErro.contains("campo1: não pode ser nulo"));
        assertTrue(mensagemErro.contains("campo2: inválido"));
        assertEquals(400, erro.getStatus());
        assertEquals("BAD_REQUEST", erro.getError());
        assertEquals("/api/test", erro.getPath());
        assertNotNull(erro.getTimestamp());
    }

    @Test
    void deveTratarErroDeParseEnumComMensagemEspecifica() {
        String mensagemEx = "Cannot deserialize value of type `pos.java.bora_comer.infra.delivery.userType.dto.UserTypeNameRequestEnum` from String \"INVALIDO\"";
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(mensagemEx);

        ErroView erro = exceptionHandler.handleEnumParseError(ex, request);

        assertTrue(erro.getMessage().contains("Valor inválido para o tipo de usuário"));
        assertTrue(erro.getMessage().contains("CLIENTE"));
        assertTrue(erro.getMessage().contains("DONO_RESTAURANTE"));
        assertEquals(400, erro.getStatus());
        assertEquals("BAD_REQUEST", erro.getError());
        assertEquals("/api/test", erro.getPath());
        assertNotNull(erro.getTimestamp());
    }

    @Test
    void deveTratarErroDeParseGenericoComMensagemPadrao() {
        String mensagemEx = "Erro de parse genérico";
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(mensagemEx);

        ErroView erro = exceptionHandler.handleEnumParseError(ex, request);

        assertEquals("Erro ao processar a requisição. Verifique os dados enviados.org.springframework.http.converter.HttpMessageNotReadableException: Erro de parse genérico", erro.getMessage());
        assertEquals(400, erro.getStatus());
        assertEquals("BAD_REQUEST", erro.getError());
        assertEquals("/api/test", erro.getPath());
        assertNotNull(erro.getTimestamp());
    }
}
