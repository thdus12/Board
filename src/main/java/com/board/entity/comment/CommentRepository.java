package com.board.entity.comment;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 댓글 엔티티에 대한 데이터 액세스를 수행하는 JpaRepository 인터페이스
 */
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // 게시글의 댓글 개수를 가져오기 위한 쿼리 문자열
    static final String SELECT_COMMENT_CNT = "SELECT COUNT(content) "
            + "FROM comment "
            + "WHERE board_id = :boardId";
    
    // 댓글 업데이트를 위한 쿼리 문자열
    static final String UPDATE_BOARD = "UPDATE board "
            + "SET title = :#{#boardRequestDto.title}, "
            + "content = :#{#boardRequestDto.content}, "
            + "update_time = NOW() "
            + "WHERE id = :#{#boardRequestDto.id}";
    
    /**
     * 게시글 ID에 해당하는 댓글 목록을 정렬하여 조회하는 메서드
     *
     * @param boardId 게시글의 ID
     * @param sort    정렬 정보
     * @return 게시글 ID에 해당하는 댓글 목록
     */
    List<CommentEntity> findByBoardId(Long boardId, Sort sort);

    /**
     * 부모 댓글 ID에 해당하는 대댓글 목록을 조회하는 메서드
     *
     * @param parentId 부모 댓글의 ID
     * @return 부모 댓글 ID에 해당하는 대댓글 목록
     */
    List<CommentEntity> findByParentId(Long parentId);

    /**
     * 게시글의 댓글 수를 가져오는 메서드
     *
     * @param boardId 게시글의 ID
     * @return 게시글의 댓글 수
     */
    @Transactional
    @Query(value = SELECT_COMMENT_CNT, nativeQuery = true)
    Long countCommentsByBoardId(@Param("boardId") Long boardId);
}
