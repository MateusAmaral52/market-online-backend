package com.market.online.mapper;

import com.market.online.dto.request.ProductRequestDTO;
import com.market.online.dto.response.ProductResponseDTO;
import com.market.online.entity.Product;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ProductMapper {

    private ProductMapper(){
        // evita instanciação
    }

    public static Product toEntity(ProductRequestDTO dto){
        return new Product(
                dto.nameProduct(),
                dto.price(),
                dto.category(),
                dto.stock(),
                dto.imageUrl()
        );
    }

    public static ProductResponseDTO toResponseDTO(Product product){
        return new ProductResponseDTO(
                product.getIdProduct(),
                product.getNameProduct(),
                product.getPrice(),
                product.getCategory(),
                product.getStock(),
                product.getImageUrl()
        );
    }

    //Para novo findAllProducts utilizando paginação (completa)
    //Para melhorar a visulização dos dados no retorno da paginação
    public ProductResponseDTO fromEntity(Product product) {
        if (product == null)
            throw new IllegalArgumentException("Produto não pode ser nulo!!!");

        return new ProductResponseDTO(
                product.getIdProduct(),
                product.getNameProduct(),
                product.getPrice(),
                product.getCategory(),
                product.getStock(),
                product.getImageUrl()
        );
    }

    public List<ProductResponseDTO> fromEntityList(List<Product> products) {
        if (products == null || products.isEmpty())
            return Collections.emptyList();

        return products.stream()
                .map(this::fromEntity)
                .toList();
    }
}
