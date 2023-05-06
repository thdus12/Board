package com.board.dto.board;

import java.time.LocalDateTime;

import com.board.entity.board.BoardEntity;

import lombok.Getter;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String registerId;
    private int readCnt;
    private LocalDateTime registerTime;

    public BoardResponseDto(BoardEntity board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.readCnt = board.getReadCnt();
        this.registerId = board.getRegisterId();
        this.registerTime = board.getRegisterTime();
    }

    @Override
    public String toString() {
        return "BoardListDto ["
        		+ "id=" + id 
        		+ ", title=" + title 
        		+ ", content=" + content 
        		+ ", readCnt=" + readCnt 
        		+ ", registerId=" + registerId 
        		+ ", registerTime=" + registerTime + "]";
    }
}