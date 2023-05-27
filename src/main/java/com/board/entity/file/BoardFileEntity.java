package com.board.entity.file;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.board.entity.BaseTimeEntity;
import com.board.entity.board.BoardEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시글 첨부 파일 정보를 담고 있는 엔티티 클래스
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "board_file")
public class BoardFileEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                   // 첨부 파일의 고유 식별자(ID)
    @ManyToOne(fetch = FetchType.LAZY)
    private BoardEntity board;         // 첨부 파일이 속한 게시글 정보
    private String origFileName;       // 원본 파일명
    private String saveFileName;       // 저장 파일명
    private int fileSize;              // 파일 크기
    private String fileExt;            // 파일 확장자
    private String filePath;           // 파일 경로
    private String deleteYn;           // 파일 삭제 여부

    /**
     * BoardFileEntity의 생성자
     *
     * @param id            첨부 파일의 고유 식별자(ID)
     * @param board         첨부 파일이 속한 게시글 정보
     * @param origFileName  원본 파일명
     * @param saveFileName  저장 파일명
     * @param fileSize     파일 크기
     * @param fileExt      파일 확장자
     * @param filePath     파일 경로
     * @param deleteYn     파일 삭제 여부
     */
    @Builder
    public BoardFileEntity(Long id, BoardEntity board, String origFileName, String saveFileName, int fileSize, String fileExt,
        String filePath, String deleteYn) {
        this.id = id;
        this.board = board;
        this.origFileName = origFileName;
        this.saveFileName = saveFileName;
        this.fileSize = fileSize;
        this.fileExt = fileExt;
        this.filePath = filePath;
        this.deleteYn = deleteYn;
    }
}
