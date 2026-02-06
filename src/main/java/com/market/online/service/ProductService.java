package com.market.online.service;

import com.market.online.entity.Product;
import com.market.online.exception.ResourceNotFoundException;
import com.market.online.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(Long idProduct){
        return productRepository.findById(idProduct).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + idProduct +"!"));
    }

    public Product update(Long idProduct, Product updatedProduct){
        Product existingProduct = findById(idProduct);

        // Atualizações controladas (sem setter público)
        existingProduct.updateProduct(
                updatedProduct.getNameProduct(),
                updatedProduct.getPrice(),
                updatedProduct.getCategory(),
                updatedProduct.getStock(),
                updatedProduct.getImageUrl()
        );
        return productRepository.save(existingProduct);
    }

    public void delete(Long idProduct) {
        Product product = findById(idProduct);
        productRepository.delete(product);
    }
}
