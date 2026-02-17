package com.market.online.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Após criado o arquivo de configuração InternationalizationConfig.java na pasta config,
    //foi adicionado a códificação abaixo para essa nova estrutura de código
    private final MessageSource messageSource;

    //Injeção por Construtor
    public GlobalExceptionHandler(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    //Validação (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request,
            //Após criado o arquivo de configuração InternationalizationConfig.java na pasta config,
            //foi adicionado a códificação abaixo para essa nova estrutura de código
            Locale locale
    ){
        Map<String, String> errors = new HashMap<>();
        //Após criado o arquivo de configuração InternationalizationConfig.java na pasta config,
        //foi adicionado a códificação abaixo para essa nova estrutura de código
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(
                    error.getField(),
                    error.getDefaultMessage()
            );

        });

        //Após criado o arquivo de configuração InternationalizationConfig.java na pasta config,
        //foi adicionado a códificação abaixo para essa nova estrutura de código
        ApiErrorResponse response = new ApiErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação!",
                messageSource.getMessage("error.validation", null, locale),
                request.getRequestURI(),
                errors
        );
        //return ResponseEntity.badRequest().body(response);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //Recurso Não Encontrado
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ){
        ApiErrorResponse response = new ApiErrorResponse(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado!",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //Erro Genérico (FallBack)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ){
        ApiErrorResponse response = new ApiErrorResponse(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro do Servidor Interno!",
                "Ocorreu um erro inesperado!",
                request.getRequestURI(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    //Erro Requisição Inválida (BadRequest) - Campos Inválidos (Categoria Inválida / Preço Inválido / JSON Malformado)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {

        String message = "Erro de leitura da requisição!";

        Throwable cause = ex.getCause();
        if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException invalidFormat) {

            Class<?> targetType = invalidFormat.getTargetType();

            if (targetType.isEnum()) {
                message = "Valor inválido para o campo de categoria!";
            } else {
                message = "Tipo de dado inválido para um dos campos!";
            }

        } else if (cause instanceof com.fasterxml.jackson.core.JsonParseException) {
            message = "JSON malformado na requisição!";
        }

        ApiErrorResponse response = new ApiErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação!",
                message,
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}