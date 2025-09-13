package com.amazingshop.personal.productservice.services;

import com.amazingshop.personal.productservice.dto.requests.ProductDTO;
import com.amazingshop.personal.productservice.dto.requests.ProductRequest;
import com.amazingshop.personal.productservice.models.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterServiceTest {

    private final ConverterService converterService = new ConverterService();

    @Test
    void shouldConvertProductToProductDTO(){
        // Arrange (подготовка)
        Product product = Product.builder()
                .id(100L)
                .name("Laptop")
                .price(BigDecimal.valueOf(1000))
                .stock(8)
                .build();

        // Act (действие)
        ProductDTO productDTO = converterService.convertedToProductDTO(product);

        // Assert (проверка)
        assertThat(productDTO.getId()).isEqualTo(100L);
        assertThat(productDTO.getName()).isEqualTo("Laptop");
        assertThat(productDTO.getPrice()).isEqualTo(BigDecimal.valueOf(1000));
        assertThat(productDTO.getStock()).isEqualTo(8);
    }

    @Test
    void shouldConvertProductRequestToProduct(){
        // Arrange (подготовка)
        ProductRequest request = ProductRequest.builder()
                .name("Iphone")
                .price(BigDecimal.valueOf(999))
                .stock(15)
                .build();

        // Act (действие)
        Product product = converterService.convertedToProduct(request);

        // Assert (проверка)
        assertThat(product.getName()).isEqualTo("Smartphone");
        assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(999));
        assertThat(product.getStock()).isEqualTo(15);
        assertThat(product.getId()).isNull();
    }
}


