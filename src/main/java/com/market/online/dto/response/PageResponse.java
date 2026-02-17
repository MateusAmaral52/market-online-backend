package com.market.online.dto.response;

import java.util.List;

//Para melhorar a visulização dos dados no retorno da paginação
public record PageResponse<T> (
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}

