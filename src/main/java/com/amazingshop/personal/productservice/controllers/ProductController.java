package com.amazingshop.personal.productservice.controllers;

import com.amazingshop.personal.productservice.dto.requests.ProductDTO;
import com.amazingshop.personal.productservice.models.Product;
import com.amazingshop.personal.productservice.services.ConverterService;
import com.amazingshop.personal.productservice.dto.responses.ProductResponse;
import com.amazingshop.personal.productservice.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ConverterService converterService;

    @Autowired
    public ProductController(ProductService productService, ConverterService converterService) {
        this.productService = productService;
        this.converterService = converterService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long productId){
        log.warn("Product request: {}", productId);
        return ResponseEntity.ok(converterService.convertedToProductDTO(productService.findProductByIdOrThrow(productId)));
    }

    @GetMapping
    public ResponseEntity<ProductResponse> getAllProducts(){
        List<Product> products = productService.findAll();
        List<ProductDTO> productDTOs = products.stream()
                .map(converterService::convertedToProductDTO)
                .toList();
        log.info("All products requested, count: {}", productDTOs.size());
        return ResponseEntity.ok(new ProductResponse(productDTOs));
    }
}