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

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 기능 : 게시판 게시글에 대한 정보를 저장
// Lombok 라이브러리를 사용하여 기본 생성자를 생성하고, 해당 클래스의 접근 레벨을 PROTECTED로 설정
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter // Lombok 라이브러리를 사용하여 게터 메서드를 자동 생성
@Entity(name = "board") // JPA 엔티티로 선언합니다.
public class BoardEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 			 // 게시글의 고유 식별자. 자동 생성
	private String title; 		 // 게시글의 제목
	private String content;	 	 // 게시글의 내용
	private String registerId;   // 게시글을 등록한 사용자의 식별자
	private int readCnt; 		 // 게시글의 조회수
	private int upvoteCount;	 // 게시글의 추천수
	private int downvoteCount;	 // 게시글의 비추천수
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
    private MemberEntity member;
	
	// Lombok 라이브러리를 사용하여 빌더 패턴을 적용한 생성자를 생성
	@Builder
	public BoardEntity(Long id, 
					   String title, 
					   String content, 
					   int readCnt, 
					   String registerId,
					   int upvoteCount,
					   int downvoteCount,
					   MemberEntity member
					   ) {
	    this.id = id;
	    this.title = title;
	    this.content = content;
	    this.readCnt = readCnt;
	    this.registerId = registerId;
	    this.upvoteCount = upvoteCount;
	    this.downvoteCount = downvoteCount;
	    this.member = member;
	}
}