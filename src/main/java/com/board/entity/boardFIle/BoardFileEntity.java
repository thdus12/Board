package com.board.entity.boardFIle;

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

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BoardFileEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private BoardEntity board;
    private String origFileName;
    private String saveFileName;
    private int fileSize;
    private String fileExt;
    private String filePath;
    private String deleteYn;

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
