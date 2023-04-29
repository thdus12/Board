package com.board.entity.board.file;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
    
    // BOARD_FILE 테이블에서 BOARD_ID에 해당하는 파일 ID를 조회하는 쿼리
    static final String SELECT_FILE_ID= "SELECT ID FROM board_file "
            + "WHERE BOARD_ID = :boardId AND DELETE_YN != 'Y'";

    // DELETE_YN을 'Y'로 변경하는 쿼리
    static final String UPDATE_DELETE_YN= "UPDATE board_file "
            + "SET DELETE_YN = 'Y' "
            + "WHERE ID IN (:deleteIdList)";

    // BOARD_ID에 해당하는 파일의 DELETE_YN을 'Y'로 변경하는 쿼리
    static final String DELETE_BOARD_FILE_YN= "UPDATE board_file "
            + "SET DELETE_YN = 'Y' "
            + "WHERE BOARD_ID IN (:boardIdList)";

    // BOARD_ID에 해당하는 파일 ID 목록을 조회하는 메소드
    @Query(value = SELECT_FILE_ID, nativeQuery = true)
    public List<Long> findByBoardId(@Param("boardId") Long boardId);

    // DELETE_YN을 'Y'로 변경하는 메소드
    @Transactional
    @Modifying
    @Query(value = UPDATE_DELETE_YN, nativeQuery = true)
    public int updateDeleteYn(@Param("deleteIdList") Long[] deleteIdList);

    // BOARD_ID에 해당하는 파일의 DELETE_YN을 'Y'로 변경하는 메소드
    @Transactional
    @Modifying
    @Query(value = DELETE_BOARD_FILE_YN, nativeQuery = true)
    public int deleteBoardFileYn(@Param("boardIdList") Long[] boardIdList);
}
