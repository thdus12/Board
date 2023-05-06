package com.board.dto.comment;

import com.board.entity.board.BoardEntity;
import com.board.entity.comment.CommentEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
	private Long id;
    private String content;
    private String registerId;
    private BoardEntity board;

    public CommentEntity toEntity() {
        return CommentEntity.builder()
                .id(id)
                .content(content)
                .registerId(registerId)
                .board(board)
                .build();
    }
}