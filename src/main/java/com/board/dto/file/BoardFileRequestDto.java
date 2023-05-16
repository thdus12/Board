package com.board.dto.file;

import com.board.entity.board.BoardEntity;
import com.board.entity.file.BoardFileEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardFileRequestDto {
	private Long id;
    private String origFileName;
    private String saveFileName;
    private int fileSize;
    private String fileExt;
    private String filePath;
    private String deleteYn;
    private BoardEntity board;
    private Long[] idArr;
    private String fileId;
    
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