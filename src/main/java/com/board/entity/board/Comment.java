package com.board.entity.board;

import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.board.entity.BaseTimeEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class Comment extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String registerId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
    private LocalDateTime updateTime;
    
    @Builder
    public Comment(Long id, String content, String registerId, Board board, LocalDateTime updateTime) {
    	this.id = id;
    	this.content = content;
    	this.registerId = registerId;
    	this.board = board;
    	this.updateTime = updateTime;
    }
}