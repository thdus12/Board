package com.board.entity.comment;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.board.entity.BaseTimeEntity;
import com.board.entity.board.BoardEntity;
import com.board.entity.member.MemberEntity;

/**
 * 게시글의 댓글 정보를 담고 있는 엔티티 클래스
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity(name = "comment")
public class CommentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                   // 댓글의 고유 식별자(ID)
    private String content;            // 댓글의 내용
    private String registerId;         // 댓글을 등록한 사용자의 식별자
    private Long parentId;             // 댓글의 부모 댓글 ID
    private Long depth;                // 댓글의 깊이 (계층 구조를 나타내기 위해 사용)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board;         // 댓글이 속한 게시글 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;       // 댓글을 작성한 회원 정보

    /**
     * CommentEntity의 생성자
     *
     * @param id           댓글의 고유 식별자(ID)
     * @param content      댓글의 내용
     * @param registerId   댓글을 등록한 사용자의 식별자
     * @param board        댓글이 속한 게시글 정보
     * @param parentId     댓글의 부모 댓글 ID
     * @param depth        댓글의 깊이 (계층 구조를 나타내기 위해 사용)
     * @param member       댓글을 작성한 회원 정보
     */
    @Builder
    public CommentEntity(Long id, String content, String registerId, BoardEntity board, Long parentId, Long depth, MemberEntity member) {
        this.id = id;
        this.content = content;
        this.registerId = registerId;
        this.board = board;
        this.parentId = parentId;
        this.depth = depth;
        this.member = member;
    }
}
