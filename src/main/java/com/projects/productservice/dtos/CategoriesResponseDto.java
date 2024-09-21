package com.projects.productservice.dtos;

import com.projects.productservice.models.Category;
import lombok.Data;

import java.util.List;
@Data
public class CategoriesResponseDto {
    private List<Category> categoryList;
    private Response response;
}
