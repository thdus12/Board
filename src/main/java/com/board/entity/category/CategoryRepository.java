package com.board.entity.category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CategoryEntity에 대한 데이터 액세스를 수행하는 JpaRepository 인터페이스
 * 기본 CRUD 연산 외에도 카테고리 이름으로 카테고리를 검색하는 메소드를 제공
 */
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
	CategoryEntity findByName(String name);
	Optional<CategoryEntity> findById(Long categoryId);
}