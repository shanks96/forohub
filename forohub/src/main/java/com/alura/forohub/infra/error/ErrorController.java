package com.alura.forohub.infra.error;

import com.alura.forohub.infra.exception.DontExist;
import com.alura.forohub.infra.exception.InvalidInfo;
import com.alura.forohub.infra.exception.InvalidToken;
import com.alura.forohub.infra.exception.TokenIssues;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler({EntityNotFoundException.class, DontExist.class})
    public ResponseEntity<String> Error404(){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler({MethodArgumentNotValidException.class, InvalidInfo.class})
    public ResponseEntity<List<Object>> tratarError400(MethodArgumentNotValidException methodArgumentNotValidException){
        var errores = methodArgumentNotValidException.getFieldErrors().stream().map(ErrorDTO::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler({InvalidToken.class, TokenIssues.class,IllegalStateException.class})
    public ResponseEntity<String> attendInvalidToken(InvalidToken e){
        return ResponseEntity.status(401).body("Token inválido");
    }
    private record ErrorDTO(String campo, String error){
        public ErrorDTO(FieldError error){
            this(error.getField(),error.getDefaultMessage());
        }
    }
}
