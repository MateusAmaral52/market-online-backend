package com.market.online.dto.request;

import com.market.online.entity.enums.ProductCategory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequestDTO(

    @NotBlank(message = "O Nome do Produto é obrigatório!")
    String nameProduct,

    @NotNull(message = "O Preço do Produto é obrigatório!")
    @Positive(message = "O Preço do Produto deve ser maior que zero!")
    BigDecimal price,

    @NotNull(message = "A Categoria do Produto é obrigatória!")
    ProductCategory category,

    @Min(value = 0, message = "O Estoque deste Produto não pode ser negativo!")
    Integer stock,

    String imageUrl

) {
}
