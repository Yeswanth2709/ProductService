package com.projects.productservice.controllers;

import com.projects.productservice.dtos.ProductResponseDto;
import com.projects.productservice.dtos.ProductsResponseDto;
import com.projects.productservice.dtos.Response;
import com.projects.productservice.dtos.ResponseStatus;
import com.projects.productservice.models.Product;
import com.projects.productservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") long id) {
        ProductResponseDto responseDto = new ProductResponseDto();
        try{
            Product product = productService.getProductById(id);
            responseDto.setProduct(product);
            responseDto.setResponse(Response.getSuccessResponse());
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (Exception e){
            responseDto.setResponse(Response.getFailureResponse(e.getMessage()));
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping()
    public ResponseEntity<ProductsResponseDto> getAllProducts() {
        ProductsResponseDto responseDto = new ProductsResponseDto();
        responseDto.setProductList(productService.getAllProducts());
        responseDto.setResponse(Response.getSuccessResponse());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
