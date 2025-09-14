package com.amazingshop.personal.productservice.controllers;

import com.amazingshop.personal.productservice.dto.requests.ProductRequest;
import com.amazingshop.personal.productservice.dto.requests.ProductDTO;
import com.amazingshop.personal.productservice.models.Product;
import com.amazingshop.personal.productservice.services.ConverterService;
import com.amazingshop.personal.productservice.dto.responses.ProductResponse;
import com.amazingshop.personal.productservice.services.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
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

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductRequest request){

        log.info("Creating product: {}", request);
        Product product = converterService.convertedToProduct(request);
        Product savedProduct = productService.save(product);
        return ResponseEntity.created(URI.create("/api/v1/products/" + savedProduct.getId())).
                body(converterService.convertedToProductDTO(savedProduct));
    }
}