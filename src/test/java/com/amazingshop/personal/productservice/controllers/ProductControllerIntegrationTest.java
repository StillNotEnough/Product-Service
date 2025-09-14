package com.amazingshop.personal.productservice.controllers;

import com.amazingshop.personal.productservice.dto.requests.ProductDTO;
import com.amazingshop.personal.productservice.dto.requests.ProductRequest;
import com.amazingshop.personal.productservice.dto.responses.ErrorResponse;
import com.amazingshop.personal.productservice.dto.responses.ProductResponse;
import com.amazingshop.personal.productservice.models.Product;
import com.amazingshop.personal.productservice.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class ProductControllerIntegrationTest {

    private TestRestTemplate restTemplate;
    private ProductRepository productRepository;

    @Autowired
    public ProductControllerIntegrationTest(TestRestTemplate restTemplate, ProductRepository productRepository) {
        this.restTemplate = restTemplate;
        this.productRepository = productRepository;
    }

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void shouldReturnProductWhenExists() {
        Product savedProduct = productRepository.save(
                new Product("iPhone 15", BigDecimal.valueOf(999.99), 10));

        ResponseEntity<ProductDTO> response = restTemplate.getForEntity(
                "/api/v1/products/" + savedProduct.getId(), ProductDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(savedProduct.getId());
        assertThat(response.getBody().getName()).isEqualTo("iPhone 15");
        assertThat(response.getBody().getPrice()).isEqualTo(BigDecimal.valueOf(999.99));
        assertThat(response.getBody().getStock()).isEqualTo(10);
    }

    @Test
    void shouldReturn404WhenProductNotFound() {
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(
                "/api/v1/products/999999", ErrorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getMessage()).contains("не найден");
    }

    @Test
    void shouldCreateProductWhenValidRequest() {
        ProductRequest request = ProductRequest.builder()
                .name("Samsung Galaxy watch")
                .price(BigDecimal.valueOf(899.99))
                .stock(25)
                .build();

        ResponseEntity<ProductDTO> response = restTemplate.postForEntity(
                "/api/v1/products", request, ProductDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED); // или HttpStatus.OK — зависит от вашего кода
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Samsung Galaxy watch");
        assertThat(response.getBody().getPrice()).isEqualTo(BigDecimal.valueOf(899.99));
        assertThat(response.getBody().getStock()).isEqualTo(25);
        assertThat(response.getBody().getId()).isNotNull();

        List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(1);
        assertThat(allProducts.get(0).getName()).isEqualTo("Samsung Galaxy watch");
    }

    @Test
    void shouldReturn400WhenNameIsBlank() {
        ProductRequest request = ProductRequest.builder()
                .name("")
                .price(BigDecimal.valueOf(100))
                .stock(5)
                .build();

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                "/api/v1/products",
                request,
                ErrorResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).contains("Name cannot be blank");
    }

    @Test
    void shouldReturn400WhenPriceTooLow() {
        ProductRequest request = ProductRequest.builder()
                .name("Cheap Item")
                .price(BigDecimal.valueOf(0.00))
                .stock(1)
                .build();

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                "/api/v1/products",
                request,
                ErrorResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).contains("Price must be greater than 0");
    }

    @Test
    void shouldReturnAllProducts() {
        productRepository.save(new Product("Laptop", BigDecimal.valueOf(1200), 5));
        productRepository.save(new Product("Mouse", BigDecimal.valueOf(25), 20));

        ResponseEntity<ProductResponse> response = restTemplate.getForEntity(
                "/api/v1/products",
                ProductResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getProducts()).hasSize(2);
        assertThat(response.getBody().getProducts().get(0).getName()).isIn("Laptop", "Mouse");
    }
}