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
}
