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
    //Para novo findAllProducts utilizando paginação simples
    /*
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
    */

    //Para novo findAllProducts utilizando paginação
    //Para melhorar a visulização dos dados no retorno da paginação
    //Migrado para ProductMapper
    /*
    public static ProductResponseDTO fromEntity(Product product){
        return new ProductResponseDTO(
                product.getIdProduct(),
                product.getNameProduct(),
                product.getPrice(),
                product.getCategory(),
                product.getStock(),
                product.getImageUrl()
        );
    }
    */
}
