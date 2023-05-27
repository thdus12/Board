package com.board.dto.file;

import com.board.entity.board.BoardEntity;
import com.board.entity.file.BoardFileEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 게시글 파일에 대한 정보를 전달하는 Data Transfer Object 클래스
 */
@Data
@NoArgsConstructor
public class BoardFileRequestDto {
    private Long id;                // 파일의 고유 식별자(ID)
    private String origFileName;    // 원본 파일명
    private String saveFileName;    // 저장된 파일명
    private int fileSize;           // 파일 크기
    private String fileExt;         // 파일 확장자
    private String filePath;        // 파일 경로
    private String deleteYn;        // 삭제 여부
    private BoardEntity board;      // 파일이 속한 게시글 (BoardEntity 객체)
    private Long[] idArr;           // 파일 ID 배열
    private String fileId;          // 파일 식별자

    /**
     * DTO 객체를 BoardFileEntity 객체로 변환하는 메서드
     *
     * @return BoardFileEntity : 파일의 정보를 담은 엔티티 객체
     */
    public BoardFileEntity toEntity() {
        return BoardFileEntity.builder()
                .id(id)
                .origFileName(origFileName)
                .saveFileName(saveFileName)
                .fileSize(fileSize)
                .fileExt(fileExt)
                .filePath(filePath)
                .deleteYn(deleteYn)
                .board(board)
                .build();
    }
}