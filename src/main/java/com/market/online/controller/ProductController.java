package com.market.online.controller;

import com.market.online.service.ProductService;
import com.market.online.dto.request.ProductRequestDTO;
import com.market.online.dto.response.ProductResponseDTO;
import com.market.online.entity.Product;
import com.market.online.mapper.ProductMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //Teste geral para chamada do ControllerAdvice
    /*
    @GetMapping("/test-error")
    public void testError() {
        throw new RuntimeException("Test ControllerAdvice");
    }
    */

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestBody @Valid ProductRequestDTO requestDTO
    ) {
        Product product = ProductMapper.toEntity(requestDTO);
        Product savedProduct = productService.save(product);

        ProductResponseDTO responseDTO = ProductMapper.toResponseDTO(savedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateSpecificProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductRequestDTO requestDTO
    ) {
        Product updatedProduct = ProductMapper.toEntity(requestDTO);
        Product savedProduct = productService.update(id, updatedProduct);

        return ResponseEntity.ok(ProductMapper.toResponseDTO(savedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //Get allProducts antigo, comentado e substituido pelo outro com paginação:
    /*
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.findAll()
                .stream()
                .map(ProductMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(products);
    }
    */

    //Novo getAllProducts utilizando paginação
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(defaultValue = "idProduct") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction dir = direction.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort));
        Page<ProductResponseDTO> result = productService.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(ProductMapper.toResponseDTO(product));
    }
}
