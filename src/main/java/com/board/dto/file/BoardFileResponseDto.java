package com.board.dto.file;

import com.board.entity.board.BoardEntity;
import com.board.entity.file.BoardFileEntity;
import lombok.Getter;

@Getter
public class BoardFileResponseDto {
    private Long id;
    private String origFileName;
    private String saveFileName;
    private int fileSize;
    private String fileExt;
    private String filePath;
    private String deleteYn;
    private BoardEntity board;

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

    @Override
    public String toString() {
        return "FileMstResponseDto [id=" + id +
        		", board=" + board +
        		", origFileName=" + origFileName + 
        		", saveFileName=" + saveFileName + 
        		", fileSize=" + fileSize + 
        		", fileExt=" + fileExt + 
        		", filePath=" + filePath + 
        		", deleteYn=" + deleteYn + "]";
    }
}