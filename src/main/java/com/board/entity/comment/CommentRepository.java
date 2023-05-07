package com.board.entity.comment;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByBoardId(Long boardId, Sort sort);
}