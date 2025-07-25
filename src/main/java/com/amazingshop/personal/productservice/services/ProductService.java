package com.amazingshop.personal.productservice.services;

import com.amazingshop.personal.productservice.models.Product;
import com.amazingshop.personal.productservice.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public Product findProductByIdOrThrow(Long productId) {
        return productRepository.findById(productId).orElseThrow(() ->
                new IllegalArgumentException("Товар с ID " + productId + " не найден"));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
