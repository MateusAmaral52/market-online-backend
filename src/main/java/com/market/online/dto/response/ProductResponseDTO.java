package com.market.online.dto.response;

import com.market.online.entity.Product;
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
    //Para novo findAllProducts utilizando paginação
    public ProductResponseDTO(Product product) {
        this(
                product.getIdProduct(),
                product.getNameProduct(),
                product.getPrice(),
                product.getCategory(),
                product.getStock(),
                product.getImageUrl()
        );
    }
}
