package com.board.entity.comment;

import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.board.entity.BaseTimeEntity;
import com.board.entity.board.BoardEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity(name = "comment")
public class CommentEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String registerId;
    @ManyToOne(fetch = FetchType.LAZY)
    private BoardEntity board;
    private LocalDateTime updateTime;
    
    @Builder
    public CommentEntity(Long id, String content, String registerId, BoardEntity board, LocalDateTime updateTime) {
    	this.id = id;
    	this.content = content;
    	this.registerId = registerId;
    	this.board = board;
    	this.updateTime = updateTime;
    }
}