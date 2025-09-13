package com.amazingshop.personal.productservice.services;

import com.amazingshop.personal.productservice.models.Product;
import com.amazingshop.personal.productservice.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindProductByIdWhenExists(){
        Long productId = 1L;
        Product expectedProduct = new Product("tomato", BigDecimal.valueOf(23), 33);
        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));

        Product result = productService.findProductByIdOrThrow(productId);

        assertThat(result).isEqualTo(expectedProduct);
        verify(productRepository).findById(productId);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound(){
        Long productId = 99L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                productService.findProductByIdOrThrow(99L));

        assertThat(exception.getMessage()).contains("Товар с ID 99 не найден");
        verify(productRepository).findById(productId);
    }

    @Test
    void shouldReturnAllProducts(){
        Product product1 = new Product("product1", BigDecimal.valueOf(10), 2);
        Product product2 = new Product("product2", BigDecimal.valueOf(5), 20);

        List<Product> products = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(product1, product2);
        verify(productRepository).findAll();
    }

    @Test
    void shouldSaveProduct(){
        Product product = new Product("Watch", BigDecimal.valueOf(150), 3);
        Product savedProduct = Product.builder()
                .id(1L)
                .name("Watch")
                .price(BigDecimal.valueOf(150))
                .stock(3)
                .build();

        when(productRepository.save(product)).thenReturn(savedProduct);

        Product result = productService.save(product);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Watch");
        verify(productRepository).save(product);
    }
}
