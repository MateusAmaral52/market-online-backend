package com.market.online.service;

import com.market.online.entity.Product;
import com.market.online.exception.ResourceNotFoundException;
import com.market.online.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        //Impede valor zero para produto
        validatePrice(product.getPrice());
        //Controla estoque para não ser inserido nullo. Se não for preenchido salvar com Estoque 0.
        product.setStock(resolveStock(product.getStock()));
        return productRepository.save(product);
    }

    //Impede valor zero para produto
    private void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O preço do Produto deve ser maior que zero!");
        }
    }

    //Controla estoque para não ser inserido nullo. Se não for preenchido salvar com Estoque 0.
    private int resolveStock(Integer stock) {
        return stock != null ? stock : 0;
        /*
        if (stock != null) {
            return stock;
        } else {
            return 0;
        }
        */
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(Long idProduct){
        return productRepository.findById(idProduct).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + idProduct +"!"));
    }

    public Product update(Long idProduct, Product updatedProduct){
        Product existingProduct = findById(idProduct);

        //valida estoque - não pode ser vazio e nulo, então converte para zero
        Integer stock = updatedProduct.getStock() != null
                ? updatedProduct.getStock()
                : existingProduct.getStock();

        // Atualizações controladas (sem setter público)
        existingProduct.updateProduct(
                updatedProduct.getNameProduct(),
                updatedProduct.getPrice(),
                updatedProduct.getCategory(),
                stock,
                updatedProduct.getImageUrl()
        );
        return productRepository.save(existingProduct);
    }

    public void delete(Long idProduct) {
        Product product = findById(idProduct);
        productRepository.delete(product);
    }
}
