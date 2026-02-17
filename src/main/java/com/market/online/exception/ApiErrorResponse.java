package com.market.online.exception;

import java.time.Instant;
import java.util.Map;

public record ApiErrorResponse (
        Instant timestamp, //timestamp → auditoria / debug
        int status, //status → HTTP explícito
        String error, //error → tipo do erro
        String message, //message → mensagem geral
        String path, //path → endpoint chamado / utilizado
        Map<String, String> fieldErrors //fieldErrors → validação detalhada
) {
}