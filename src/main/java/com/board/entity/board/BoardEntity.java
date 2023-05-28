package com.board.entity.board;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.board.entity.BaseTimeEntity;
import com.board.entity.member.MemberEntity;
import com.board.entity.category.CategoryEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시판의 게시글 정보를 담고 있는 엔티티 클래스
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "board")
public class BoardEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                   // 게시글의 고유 식별자. 자동 생성
    private String title;              // 게시글의 제목
    private String content;            // 게시글의 내용
    private String registerId;         // 게시글을 등록한 사용자의 식별자
    private int readCnt;               // 게시글의 조회수
    private int upvoteCount;           // 게시글의 추천수
    private int downvoteCount;         // 게시글의 비추천수
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;       // 게시글을 작성한 회원 정보
    private int isNotice = 0;  		   // 공지사항 여부
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    /**
     * BoardEntity의 생성자
     *
     * @param id            게시글의 고유 식별자(ID)
     * @param title         게시글의 제목
     * @param content       게시글의 내용
     * @param readCnt       게시글의 조회수
     * @param registerId    게시글을 등록한 사용자의 식별자
     * @param upvoteCount   게시글의 추천수
     * @param downvoteCount 게시글의 비추천수
     * @param member        게시글을 작성한 회원 정보
     * @param isNotice      공지사항 여부
     * @param category      게시판 카테고리 정보
     */
    @Builder
    public BoardEntity(Long id, String title, String content, int readCnt, String registerId, int upvoteCount, int downvoteCount, MemberEntity member, int isNotice, CategoryEntity category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.readCnt = readCnt;
        this.registerId = registerId;
        this.upvoteCount = upvoteCount;
        this.downvoteCount = downvoteCount;
        this.member = member;
        this.isNotice = isNotice;
        this.category = category;
    }
}
