package com.projects.productservice.controllers;

import com.projects.productservice.dtos.*;
import com.projects.productservice.dtos.ResponseStatus;
import com.projects.productservice.models.Product;
import com.projects.productservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ProductsResponseDto> getAllProducts(@RequestParam(value="pageSize",defaultValue = "10") int pageSize,
                                                              @RequestParam(value="pageNum",defaultValue = "0") int pageNum) {
        ProductsResponseDto responseDto = new ProductsResponseDto();
        responseDto.setProductList(productService.getAllProducts(pageSize,pageNum));
        responseDto.setResponse(Response.getSuccessResponse());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody CreateProductRequestDto requestDto){
        ProductResponseDto responseDto = new ProductResponseDto();
        String title = requestDto.getTitle();
        String description = requestDto.getDescription();
        double price = requestDto.getPrice();
        String image = requestDto.getImage();
        String category = requestDto.getCategory();
        try{
            Product product=productService.createProduct(title,description,price,image,category);
            responseDto.setProduct(product);
            responseDto.setResponse(Response.getSuccessResponse());
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (Exception e){
            responseDto.setResponse(Response.getFailureResponse(e.getMessage()));
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<ProductResponseDto> updateProductPrice(@PathVariable("id") long productId, @RequestBody UpdateProductPriceRequestDto requestDto){
        ProductResponseDto responseDto = new ProductResponseDto();
        double price = requestDto.getPrice();
        try{
            Product product=productService.updateProductPrice(productId,price);
            responseDto.setProduct(product);
            responseDto.setResponse(Response.getSuccessResponse());
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (Exception e){
            responseDto.setResponse(Response.getFailureResponse(e.getMessage()));
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<ProductResponseDto> updateProductImage(@PathVariable("id") long productId, @RequestBody UpdateProductImageRequestDto requestDto){
        ProductResponseDto responseDto = new ProductResponseDto();
        String image = requestDto.getImage();
        try{
            Product product=productService.updateProductImage(productId,image);
            responseDto.setProduct(product);
            responseDto.setResponse(Response.getSuccessResponse());
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (Exception e){
            responseDto.setResponse(Response.getFailureResponse(e.getMessage()));
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") long productId){
        try{
            productService.deleteProduct(productId);
            return new ResponseEntity<>("Product with id "+productId+ " is deleted successfully", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
