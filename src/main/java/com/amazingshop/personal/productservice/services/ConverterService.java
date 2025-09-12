package com.amazingshop.personal.productservice.services;

import com.amazingshop.personal.productservice.dto.requests.ProductRequest;
import com.amazingshop.personal.productservice.dto.requests.ProductDTO;
import com.amazingshop.personal.productservice.models.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class ConverterService {

    private final ModelMapper modelMapper;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public ConverterService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Product convertedToProduct(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public ProductDTO convertedToProductDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public Product convertedToProduct(ProductRequest productRequest) {
        return modelMapper.map(productRequest, Product.class);
    }
}