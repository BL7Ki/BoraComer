package pos.java.bora_comer.core.errors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(SummerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroView SummerException(SummerException exception, HttpServletRequest request) {
        return new ErroView(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                exception.getMessage(),
                request.getServletPath()
        );
    }

    @ExceptionHandler(UserDomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroView SummerParseException(UserDomainException exception, HttpServletRequest request) {
        return new ErroView(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroView handleGenericException(Exception exception, HttpServletRequest request) {
        return new ErroView(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                exception.getMessage(),
                request.getServletPath()
        );
    }

    @ExceptionHandler(SummerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroView SummerNotFoundException(SummerNotFoundException exception, HttpServletRequest request) {
        return new ErroView(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                exception.getMessage(),
                request.getServletPath()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroView handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        StringBuilder errors = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.append(error.getField())
                    .append(": ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        }
        return new ErroView(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                errors.toString(),
                request.getServletPath()
        );

    }
}
