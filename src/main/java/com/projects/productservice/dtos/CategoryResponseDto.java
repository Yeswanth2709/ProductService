package com.projects.productservice.dtos;

import com.projects.productservice.models.Category;
import lombok.Data;

@Data
public class CategoryResponseDto {
    private Category category;
    private Response response;
}
