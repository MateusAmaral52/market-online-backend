package com.market.online.mapper;

import com.market.online.dto.request.ProductRequestDTO;
import com.market.online.dto.response.ProductResponseDTO;
import com.market.online.entity.Product;

public class ProductMapper {

    private ProductMapper(){
        // evita instanciação
    }

    public static Product producttoEntity(ProductRequestDTO dto){
        return new Product(
                dto.nameProduct(),
                dto.price(),
                dto.category(),
                dto.stock(),
                dto.imageUrl()
        );
    }

    public static ProductResponseDTO producttoResponseDTO(Product product){
        return new ProductResponseDTO(
                product.getIdProduct(),
                product.getNameProduct(),
                product.getPrice(),
                product.getCategory(),
                product.getStock(),
                product.getImageUrl()
        );
    }
}
