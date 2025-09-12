package com.amazingshop.personal.productservice.services;

import com.amazingshop.personal.productservice.models.Product;
import com.amazingshop.personal.productservice.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findProductByIdOrThrow(Long productId) {
        return productRepository.findById(productId).orElseThrow(() ->
                new IllegalArgumentException("Товар с ID " + productId + " не найден"));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public Product save(Product product) {
        log.info("Saving product: {}", product.getName());
        return productRepository.save(product);
    }
}
