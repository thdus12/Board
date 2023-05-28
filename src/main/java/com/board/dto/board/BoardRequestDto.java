package com.board.dto.board;

import com.board.entity.board.BoardEntity;
import com.board.entity.category.CategoryEntity;
import com.board.entity.member.MemberEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 게시판 게시글에 대한 정보를 전달하는 Data Transfer Object 클래스
 */
@Data
@NoArgsConstructor
public class BoardRequestDto {
	private Long id;  			 	 // 게시글의 ID
	private String title;  		 	 // 게시글의 제목
	private String content; 	 	 // 게시글의 내용
	private String registerId;   	 // 게시글을 등록한 사용자의 ID
	private int readCnt;  		 	 // 게시글의 조회 수
	private MemberEntity member; 	 // 게시글을 작성한 회원 정보
	private int isNotice;    	 	 // 공지사항 여부 
	private CategoryEntity category; // 카테고리 정보
	
	/**
     * DTO 객체를 BoardEntity 객체로 변환하는 메서드
     * 
     * @return BoardEntity : 게시글의 정보를 담은 엔티티 객체
     */
	public BoardEntity toEntity() {
		return BoardEntity.builder()
				.id(id)
				.title(title)
				.content(content)
				.registerId(registerId)
				.readCnt(readCnt)
				.member(member)
				.isNotice(isNotice)
				.category(category)
				.build();
	}
}
