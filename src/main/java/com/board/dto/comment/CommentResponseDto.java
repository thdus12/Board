package com.board.dto.comment;

import java.time.LocalDateTime;

import com.board.entity.board.BoardEntity;
import com.board.entity.comment.CommentEntity;
import com.board.entity.member.MemberEntity;

import lombok.Builder;
import lombok.Data;

/**
 * 게시글 댓글에 대한 정보를 응답하는 Data Transfer Object 클래스
 */
@Data
public class CommentResponseDto {
    private Long id;                    // 댓글의 고유 식별자(ID)
    private String content;             // 댓글 내용
    private String registerId;          // 댓글 작성자의 식별자
    private BoardEntity board;          // 댓글이 속한 게시글 (BoardEntity 객체)
    private MemberEntity member;        // 댓글 작성자 (MemberEntity 객체)
    private LocalDateTime registerTime; // 댓글 작성 시간
    private LocalDateTime updateTime;   // 댓글 업데이트 시간
    private Long parentId;              // 댓글의 부모 댓글 ID (답글인 경우)
    private Long depth;                 // 댓글의 깊이 (답글인 경우)

    /**
     * CommentResponseDto의 생성자
     *
     * @param id            댓글의 고유 식별자(ID)
     * @param content       댓글 내용
     * @param registerId    댓글 작성자의 식별자
     * @param board         댓글이 속한 게시글 (BoardEntity 객체)
     * @param registerTime  댓글 작성 시간
     * @param updateTime    댓글 업데이트 시간
     * @param parentId      댓글의 부모 댓글 ID (답글인 경우)
     * @param depth         댓글의 깊이 (답글인 경우)
     * @param member        댓글 작성자 (MemberEntity 객체)
     */
    @Builder
    public CommentResponseDto(Long id, String content, String registerId, BoardEntity board, LocalDateTime registerTime, LocalDateTime updateTime, Long parentId, Long depth, MemberEntity member) {
        this.id = id;
        this.content = content;
        this.registerId = registerId;
        this.board = board;
        this.registerTime = registerTime;
        this.updateTime = updateTime;
        this.parentId = parentId;
        this.depth = depth;
        this.member = member;
    }
    
    /**
     * CommentEntity 객체를 기반으로 CommentResponseDto 객체를 생성하는 생성자
     *
     * @param commentEntity CommentEntity 객체
     */
    public CommentResponseDto(CommentEntity commentEntity) {
        this.id = commentEntity.getId();
        this.content = commentEntity.getContent();
        this.registerId = commentEntity.getRegisterId();
        this.board = commentEntity.getBoard();
        this.registerTime = commentEntity.getRegisterTime();
        this.updateTime = commentEntity.getUpdateTime();
        this.parentId = commentEntity.getParentId();
        this.depth = commentEntity.getDepth();
        this.member = commentEntity.getMember();
    }
    
    /**
     * 객체를 문자열로 표현하기 위해 재정의된 toString() 메서드
     *
     * @return 객체의 문자열 표현
     */
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
                + ", depth=" + depth
                + ", member=" + member + "]";
    }
}