package com.amazingshop.personal.productservice.services;

import com.amazingshop.personal.productservice.dto.requests.ProductDTO;
import com.amazingshop.personal.productservice.dto.requests.ProductRequest;
import com.amazingshop.personal.productservice.models.Product;
import org.springframework.stereotype.Service;

@Service
public class ConverterService {

    public ProductDTO convertedToProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    public Product convertedToProduct(ProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();
    }
}