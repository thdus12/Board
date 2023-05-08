package com.board.entity.comment;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByBoardId(Long boardId, Sort sort);
    
    // 대댓글을 찾기 위한 메소드 추가
    List<CommentEntity> findByParentId(Long parentId);
}