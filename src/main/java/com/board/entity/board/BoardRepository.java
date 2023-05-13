package com.board.entity.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.board.dto.board.BoardRequestDto;

// JpaRepository를 상속받아 Board 엔티티와 관련된 데이터베이스 작업을 처리하는 인터페이스
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
	
    // 게시글 업데이트를 위한 쿼리 문자열
    static final String UPDATE_BOARD = "UPDATE board "
            + "SET title = :#{#boardRequestDto.title}, "
            + "content = :#{#boardRequestDto.content}, "
            + "update_time = NOW() "
            + "WHERE id = :#{#boardRequestDto.id}";

    // 게시글 조회수 증가를 위한 쿼리 문자열
    static final String UPDATE_BOARD_READ_CNT_INC = "UPDATE board "
            + "SET READ_CNT = read_cnt + 1 "
            + "WHERE id = :id";
    
    // 게시글 삭제를 위한 쿼리 문자열
    static final String DELETE_BOARD = "DELETE FROM board "
			+ "WHERE id IN (:deleteList)";
    
    static final String UPDATE_BOARD_UPVOTECOUNT = "UPDATE board "
    		+ "SET upvote_count = upvote_count + 1 "
    		+ "WHERE id = :id";
    
    static final String UPDATE_BOARD_DOWNVOTECOUNT = "UPDATE board "
    		+ "SET downvote_count = downvote_count + 1 "
    		+ "WHERE id = :id";

    // 게시글 업데이트를 위한 메소드
    @Transactional
    @Modifying
    @Query(value = UPDATE_BOARD, nativeQuery = true)
    public int updateBoard(@Param("boardRequestDto") BoardRequestDto boardRequestDto);

    // 게시글 조회수 증가를 위한 메소드
    @Transactional
    @Modifying
    @Query(value = UPDATE_BOARD_READ_CNT_INC, nativeQuery = true)
    public int updateBoardReadCntInc(@Param("id") Long id);
    
    // 게시글 삭제를 위한 메소드
    @Transactional
    @Modifying
    @Query(value = DELETE_BOARD, nativeQuery = true)
    public int deleteBoard(@Param("deleteList") Long[] deleteList);
    
    // 게시글 추천수를 업데이트 하기 위한 메소드
    @Transactional
    @Modifying
    @Query(value = UPDATE_BOARD_UPVOTECOUNT, nativeQuery = true)
    void updateUpvote(@Param("id") Long id);

    // 게시글 비추천수를 업데이트 하기 위한 메소드
    @Transactional
    @Modifying
    @Query(value = UPDATE_BOARD_DOWNVOTECOUNT, nativeQuery = true)
    void updateDownvote(@Param("id") Long id);
}
