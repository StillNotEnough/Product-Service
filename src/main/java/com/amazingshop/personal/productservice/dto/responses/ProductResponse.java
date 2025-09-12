package com.amazingshop.personal.productservice.util.responses;

import com.amazingshop.personal.productservice.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponse {

    private List<ProductDTO> products;

}
