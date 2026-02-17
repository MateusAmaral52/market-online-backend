package com.market.online.dto.response;

import com.market.online.entity.enums.ProductCategory;

import java.math.BigDecimal;

public record ProductResponseDTO(

    Long idProduct,
    String nameProduct,
    BigDecimal price,
    ProductCategory category,
    Integer stock,
    String imageUrl

){
}
