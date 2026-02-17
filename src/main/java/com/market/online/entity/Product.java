package com.market.online.entity;

import com.market.online.entity.enums.ProductCategory;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Products")
//@Getter   - pode usar este ao invés de adicionar os Getters lá embaixo
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    @Column(nullable = false, length = 150)
    private String nameProduct;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ProductCategory category;

    @Column(nullable = false)
    private Integer stock;

    @Column
    private String imageUrl;

    protected Product(){
        // construtor protegido exigido pelo JPA
    }

    public Product(String nameProduct,
                   BigDecimal price,
                   ProductCategory category,
                   Integer stock,
                   String imageUrl)
    {
        this.nameProduct = nameProduct;
        this.price = price;
        this.category = category;
        this.stock = stock == null ? 0 : stock;
        this.imageUrl = imageUrl;
    }

    // comportamento de domínio
    public void updateProduct(String nameProduct,
                       BigDecimal price,
                       ProductCategory category,
                       Integer stock,
                       String imageUrl)
    {
        this.nameProduct = nameProduct;
        this.price = price;
        this.category = category;
        this.stock = stock == null ? 0 : stock;
        this.imageUrl = imageUrl;
    }

    // PROXIMA ETAPA (ADICIONAR E OU REMOVER DO ESTOQUE) PARA DESENVOLVIMENTO FUTURO
    /*
        // adicionar quantidade ao estoque
        public void increaseStock(int quantity){
            this.stock += quantity;
        }

        // remover quantidade do estoque
        public void decreaseStock(int quantity){
            if(quantity <= 0)
                throw new IllegalArgumentException("Quantidade inválida!!!");

            if(quantity > stock)
                throw new IllegalArgumentException("Estoque insuficiente!!!");

            this.stock -= quantity;
        }
     */

    // getters apenas (sem setters públicos)
    public Long getIdProduct() { return idProduct; }
    public String getNameProduct() { return nameProduct; }
    public BigDecimal getPrice() { return price; }
    public ProductCategory getCategory() { return category; }
    public Integer getStock() { return stock; }
    public String getImageUrl() { return imageUrl; }

    public void setStock(Integer stock) { this.stock = stock; }
}
