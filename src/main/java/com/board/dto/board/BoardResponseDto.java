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
    private Long commentCount; // 게시글 댓글 수를 저장할 필드 추가
    private int upvoteCount;   // 게시글 추천수
    

    public BoardResponseDto(BoardEntity board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.readCnt = board.getReadCnt();
        this.registerId = board.getRegisterId();
        this.registerTime = board.getRegisterTime();
        
    }

    // 댓글 수를 설정하는 메소드 추가
    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
    
    // 게시글 추천 수를 설정하는 메소드 추가
    public void setUpvoteCount(int upvoteCount) {
        this.upvoteCount = upvoteCount;
    }

    @Override
    public String toString() {
        return "BoardListDto ["
                + "id=" + id
                + ", title=" + title
                + ", content=" + content
                + ", readCnt=" + readCnt
                + ", registerId=" + registerId
                + ", registerTime=" + registerTime
                + ", commentCount=" + commentCount + "]";
    }
}