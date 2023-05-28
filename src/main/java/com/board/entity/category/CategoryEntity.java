package com.board.entity.category;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시판의 카테고리 정보를 담고 있는 엔티티 클래스
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
