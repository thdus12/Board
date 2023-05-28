package com.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.board.entity.category.CategoryEntity;
import com.board.entity.category.CategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;
	
	public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }
	
	public CategoryEntity getCategoryEntity(Long categoryId) throws Exception {
		return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new Exception("Category not found with id: " + categoryId));
	}

	public Long getCategoryId(String categoryName) {
        CategoryEntity category = categoryRepository.findByName(categoryName);
        return category.getId();
    }
}
