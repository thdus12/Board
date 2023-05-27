package com.board.entity.file;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 첨부 파일 엔티티에 대한 데이터 액세스를 수행하는 JpaRepository 인터페이스
 */
public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {

	// 게시글 ID에 해당하는 첨부 파일 ID 목록을 조회하기 위한 쿼리 문자열
    static final String SELECT_FILE_ID = "SELECT ID FROM board_file " +
    "WHERE BOARD_ID = :boardId AND DELETE_YN != 'Y'";

    // 첨부 파일의 삭제 여부를 업데이트하기 위한 쿼리 문자열
    static final String UPDATE_DELETE_YN = "UPDATE board_file " +
    "SET DELETE_YN = 'Y' " +
    "WHERE ID IN (:deleteIdList)";

    // 게시글에 속한 첨부 파일의 삭제 여부를 업데이트하기 위한 쿼리 문자열
    static final String DELETE_BOARD_FILE_YN = "UPDATE board_file " +
    "SET DELETE_YN = 'Y' " +
    "WHERE BOARD_ID IN (:boardIdList)";

    /**
     * 게시글 ID에 해당하는 첨부 파일 ID 목록을 조회하는 메서드
     *
     * @param boardId 게시글의 ID
     * @return 게시글 ID에 해당하는 첨부 파일 ID 목록
     */
    @Query(value = SELECT_FILE_ID, nativeQuery = true)
    public List<Long> findByBoardId(@Param("boardId") Long boardId);

    /**
     * 첨부 파일의 삭제 여부를 업데이트하는 메서드
     *
     * @param deleteIdList 삭제할 첨부 파일의 ID 목록
     * @return 업데이트된 행 수
     */
    @Transactional
    @Modifying
    @Query(value = UPDATE_DELETE_YN, nativeQuery = true)
    public int updateDeleteYn(@Param("deleteIdList") Long[] deleteIdList);

    /**
     * 게시글에 속한 첨부 파일의 삭제 여부를 업데이트하는 메서드
     *
     * @param boardIdList 게시글의 ID 목록
     * @return 업데이트된 행 수
     */
    @Transactional
    @Modifying
    @Query(value = DELETE_BOARD_FILE_YN, nativeQuery = true)
    public int deleteBoardFileYn(@Param("boardIdList") Long[] boardIdList);
}
