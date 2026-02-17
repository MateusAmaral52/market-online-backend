package com.market.online.service;

import com.market.online.dto.response.PageResponse;
import com.market.online.dto.response.ProductResponseDTO;

import com.market.online.entity.Product;
import com.market.online.exception.ResourceNotFoundException;
import com.market.online.mapper.ProductMapper;
import com.market.online.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    //Antigo Constructor apenas para productRepository
    /*
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    */

    //Novo Construtor para productRepository e productMapper
    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
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

            //throw new IllegalArgumentException("O preço do Produto deve ser maior que zero!");
            //Após criado o Arquivo de configuração InternationaalizationConfig.java na pasta config,
            //substituir o códificação acima pela nova estrutura de código apresentada abaixo:

            throw new IllegalArgumentException("{product.price.invalid}");
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

    public Product findById(Long idProduct){
        return productRepository.findById(idProduct).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + idProduct +"!"));
    }

    //Antigo findAllProducts quando utilizava List para retornar todos os Produtos
    /*
    public List<Product> findAll(){
        return productRepository.findAll();
    }
    */

    //Código findAllProducts novo, para utilizar novo formato com paginação
    /*
    public Page<ProductResponseDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductResponseDTO::new);
    }
    */

    //Código findAllProducts novo, para utilizar novo formato com paginação
    //Para melhorar a visulização dos dados no retorno da paginação
    public PageResponse<ProductResponseDTO> findAll(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);

        List<ProductResponseDTO> list =
                page.getContent()
                        .stream()
                        .map(productMapper::fromEntity)
                        .toList();

        return new PageResponse<>(
                list,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
