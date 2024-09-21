package com.projects.productservice.services;

import com.projects.productservice.exceptions.CategoryNotFoundException;
import com.projects.productservice.models.Category;

import java.util.List;

public interface CategoryService {

    Category getCategoryById(long id) throws CategoryNotFoundException;

    List<Category> getAllCategories();

    Category createCategory(String categoryName);

    Category updateCategory(long id, String categoryName) throws CategoryNotFoundException;

    void deleteCategory(long id) throws CategoryNotFoundException;
}
