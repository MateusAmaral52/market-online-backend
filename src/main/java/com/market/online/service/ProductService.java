package com.market.online.service;

import com.market.online.entity.Product;
import com.market.online.exception.ResourceNotFoundException;
import com.market.online.repository.ProductRepository;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Product findProductById(Long idProduct){
        return productRepository.findById(idProduct).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + idProduct +"!"));
    }

    public Product updateProduct(Long idProduct, Product updatedProduct){
        Product existingProduct = findProductById(idProduct);

        // Atualizações controladas (sem setter público)
        existingProduct = new Product(
                updatedProduct.getNameProduct(),
                updatedProduct.getPrice(),
                updatedProduct.getCategory(),
                updatedProduct.getStock(),
                updatedProduct.getImageUrl()
        );
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long idProduct) {
        Product product = findProductById(idProduct);
        productRepository.delete(product);
    }
}
