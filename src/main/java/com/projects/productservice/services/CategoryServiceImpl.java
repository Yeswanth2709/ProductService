package com.projects.productservice.services;

import com.projects.productservice.exceptions.CategoryNotFoundException;
import com.projects.productservice.models.Category;
import com.projects.productservice.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    public CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryById(long id) throws CategoryNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            throw new CategoryNotFoundException("Category not found");
        }
        return optionalCategory.get();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(String categoryName) {
        Category category=new Category();
        category.setName(categoryName);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(long id, String categoryName) throws CategoryNotFoundException {
        Category category = getCategoryById(id);
        category.setName(categoryName);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(long id) throws CategoryNotFoundException {
        Category category = getCategoryById(id);
        categoryRepository.deleteById(id);
    }
}
