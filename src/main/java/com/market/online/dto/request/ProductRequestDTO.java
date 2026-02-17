package com.market.online.dto.request;

import com.market.online.entity.enums.ProductCategory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequestDTO(

    //Após criado o Arquivo de configuração InternationalizationConfig.java na pasta config
    //Criada a nova estrutura de código apresentada abaixo
    @NotBlank(message = "{product.name.required}")
    String nameProduct,

    @NotNull(message = "{product.price.required}")
    @Positive(message = "{product.price.invalid}")
    BigDecimal price,

    @NotNull(message = "{product.category.required}")
    ProductCategory category,

    @Min(value = 0, message = "{product.stock.invalid}")
    Integer stock,

    String imageUrl

    ){
}
