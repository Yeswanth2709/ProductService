package com.projects.productservice.dtos;

import com.projects.productservice.models.Product;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
@Data
public class ProductsResponseDto {
    private Page<Product> productList;
    private Response response;
}
