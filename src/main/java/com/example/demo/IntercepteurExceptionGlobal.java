package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class IntercepteurExceptionGlobal {

    //Intercepte toutes les erreurs de validation du model (@NotNull, @NotBlank, @Size ...)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody // Assure que la réponse est envoyée au format JSON (dans le corp de la réponse)
    public Map<String, Object> intercepteModeleInvalide(MethodArgumentNotValidException ex) {

        Map<String, Object> erreurs = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            erreurs.put(fieldName, errorMessage);
        });

        return erreurs;
    }

}