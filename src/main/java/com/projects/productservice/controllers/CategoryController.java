package com.projects.productservice.controllers;

import com.projects.productservice.dtos.*;
import com.projects.productservice.models.Category;
import com.projects.productservice.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable("id") long id) {
        CategoryResponseDto responseDto=new CategoryResponseDto();
        try{
            Category category= categoryService.getCategoryById(id);
            responseDto.setCategory(category);
            responseDto.setResponse(Response.getSuccessResponse());
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch(Exception ex){
            responseDto.setResponse(Response.getFailureResponse(ex.getMessage()));
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping()
    public ResponseEntity<CategoriesResponseDto> getAllCategories() {
        CategoriesResponseDto responseDto =new CategoriesResponseDto();
        responseDto.setCategoryList(categoryService.getAllCategories());
        responseDto.setResponse(Response.getSuccessResponse());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CreateCategoryRequestDto requestDto){
        CategoryResponseDto responseDto=new CategoryResponseDto();
        String categoryName=requestDto.getName();
        try{
            Category category= categoryService.createCategory(categoryName);
            responseDto.setCategory(category);
            responseDto.setResponse(Response.getSuccessResponse());
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch(Exception ex){
            responseDto.setResponse(Response.getFailureResponse(ex.getMessage()));
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable("id") long id, @RequestBody UpdateCategoryRequestDto requestDto){
        CategoryResponseDto responseDto=new CategoryResponseDto();
        String categoryName=requestDto.getName();
        try{
            Category category= categoryService.updateCategory(id,categoryName);
            responseDto.setCategory(category);
            responseDto.setResponse(Response.getSuccessResponse());
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch(Exception ex){
            responseDto.setResponse(Response.getFailureResponse(ex.getMessage()));
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") long id){
        try{
            categoryService.deleteCategory(id);
            return new ResponseEntity<>("Category with id "+id +" is deleted successfully", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
