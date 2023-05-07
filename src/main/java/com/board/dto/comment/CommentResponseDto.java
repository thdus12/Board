package com.board.dto.comment;

import java.time.LocalDateTime;

import com.board.entity.board.BoardEntity;
import com.board.entity.comment.CommentEntity;

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
    private LocalDateTime registerTime;
    private LocalDateTime updateTime;
    private Long parentId;
    private Long depth;

    @Builder
    public CommentResponseDto(Long id, String content, String registerId, BoardEntity board, LocalDateTime registerTime, LocalDateTime updateTime, Long parentId, Long depth) {
        this.id = id;
        this.content = content;
        this.registerId = registerId;
        this.board = board;
        this.registerTime = registerTime;
        this.updateTime = updateTime;
        this.parentId = parentId;
    	this.depth = depth;
    }
    
    public CommentResponseDto(CommentEntity commentEntity) {
        this.id = commentEntity.getId();
        this.content = commentEntity.getContent();
        this.registerId = commentEntity.getRegisterId();
        this.board = commentEntity.getBoard();
        this.registerTime = commentEntity.getRegisterTime();
        this.updateTime = commentEntity.getUpdateTime();
        this.parentId = commentEntity.getParentId();
        this.depth = commentEntity.getDepth();
    }
    
    @Override
    public String toString() {
        return "CommentListDto ["
        		+ "id=" + id
        		+ ", content=" + content 
        		+ ", registerId=" + registerId
        		+ ", board=" + board 
        		+ ", registerTime=" + registerTime
        		+ ", updateTime=" + updateTime
        		+ ", parentId=" + parentId
        		+ ", depth=" + depth + "]";
    }
}
