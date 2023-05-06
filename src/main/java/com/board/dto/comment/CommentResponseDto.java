package com.board.dto.comment;

import java.time.LocalDateTime;

import com.board.entity.board.BoardEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String registerId;
    private BoardEntity board;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;

    @Builder
    public CommentResponseDto(Long id, String content, String registerId, BoardEntity board, LocalDateTime createdTime, LocalDateTime updateTime) {
        this.id = id;
        this.content = content;
        this.registerId = registerId;
        this.board = board;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
    }
    
    @Override
    public String toString() {
        return "CommentListDto ["
        		+ "id=" + id
        		+ ", content=" + content 
        		+ ", registerId=" + registerId
        		+ ", board=" + board 
        		+ ", createdTime=" + createdTime
        		+ ", updateTime=" + updateTime + "]";
    }
}
