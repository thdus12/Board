package com.board.dto.comment;

import com.board.entity.board.BoardEntity;
import com.board.entity.comment.CommentEntity;
import com.board.entity.member.MemberEntity;

import lombok.Data;

/**
 * 게시글 댓글에 대한 정보를 전달하는 Data Transfer Object 클래스
 */
@Data
public class CommentRequestDto {
    private Long id;             // 댓글의 고유 식별자(ID)
    private String content;      // 댓글 내용
    private String registerId;   // 댓글 작성자의 식별자
    private BoardEntity board;   // 댓글이 속한 게시글 (BoardEntity 객체)
    private MemberEntity member; // 댓글 작성자 (MemberEntity 객체)
    private Long parentId;       // 댓글의 부모 댓글 ID (답글인 경우)
    private Long depth;          // 댓글의 깊이 (답글인 경우)

    /**
     * 댓글 작성자의 식별자를 설정하는 메서드
     *
     * @param registerId 댓글 작성자의 식별자
     */
    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    /**
     * DTO 객체를 CommentEntity 객체로 변환하는 메서드
     *
     * @return CommentEntity : 댓글의 정보를 담은 엔티티 객체
     */
    public CommentEntity toEntity() {
        return CommentEntity.builder()
                .id(id)
                .content(content)
                .registerId(registerId)
                .board(board)
                .member(member)
                .parentId(parentId)
                .depth(depth)
                .build();
    }
}