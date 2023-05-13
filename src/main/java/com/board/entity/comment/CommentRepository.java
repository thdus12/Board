package com.board.entity.comment;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByBoardId(Long boardId, Sort sort);
    
    // 대댓글을 찾기 위한 메소드
    List<CommentEntity> findByParentId(Long parentId);
    
    static final String SELECT_COMMENT_CNT = "SELECT COUNT(content) "
            + "FROM comment "
            + "WHERE board_id = :boardId";
    
    // 댓글 수 가져오는 메소드
    @Transactional
    @Query(value = SELECT_COMMENT_CNT, nativeQuery = true)
    Long countCommentsByBoardId(@Param("boardId") Long boardId);
}
