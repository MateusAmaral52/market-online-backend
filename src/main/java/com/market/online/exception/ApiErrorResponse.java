package com.market.online.exception;

import java.time.Instant;
import java.util.Map;

public record ApiErrorResponse (
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors

        /*
        timestamp → auditoria / debug
        status → HTTP explícito
        error → tipo do erro
        message → mensagem geral
        path → endpoint chamado / utilizado
        fieldErrors → validação detalhada
        */
) {
}
