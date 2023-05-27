package com.board.dto.file;

import com.board.entity.board.BoardEntity;
import com.board.entity.file.BoardFileEntity;
import lombok.Getter;

/**
 * 게시글 파일에 대한 정보를 응답하는 Data Transfer Object 클래스
 */
@Getter
public class BoardFileResponseDto {
    private Long id;                // 파일의 고유 식별자(ID)
    private String origFileName;    // 원본 파일명
    private String saveFileName;    // 저장된 파일명
    private int fileSize;           // 파일 크기
    private String fileExt;         // 파일 확장자
    private String filePath;        // 파일 경로
    private String deleteYn;        // 삭제 여부
    private BoardEntity board;      // 파일이 속한 게시글 (BoardEntity 객체)

    /**
     * BoardFileResponseDto의 생성자
     *
     * @param fileEntity BoardFileEntity 객체
     */
    public BoardFileResponseDto(BoardFileEntity fileEntity) {
        this.id = fileEntity.getId();
        this.origFileName = fileEntity.getOrigFileName();
        this.saveFileName = fileEntity.getSaveFileName();
        this.fileSize = fileEntity.getFileSize();
        this.fileExt = fileEntity.getFileExt();
        this.filePath = fileEntity.getFilePath();
        this.deleteYn = fileEntity.getDeleteYn();
        this.board = fileEntity.getBoard();
    }

    /**
     * 객체를 문자열로 표현하기 위해 재정의된 toString() 메서드
     *
     * @return 객체의 문자열 표현
     */
    @Override
    public String toString() {
        return "BoardFileResponseDto ["
                + "id=" + id
                + ", origFileName=" + origFileName
                + ", saveFileName=" + saveFileName
                + ", fileSize=" + fileSize
                + ", fileExt=" + fileExt
                + ", filePath=" + filePath
                + ", deleteYn=" + deleteYn
                + ", board=" + board
                + "]";
    }
}
