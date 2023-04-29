package com.board.dto.board;

import com.board.entity.board.Board;

import lombok.Data;
import lombok.NoArgsConstructor;

// 기능 : 게시판 게시물에 대한 정보를 전달하는 Data Transfer Object 클래스
@Data
@NoArgsConstructor
public class BoardRequestDto {
	private Long id;
	private String title;
	private String content;
	private String registerId;
	private int readCnt;
	
	// DTO 객체를 Board 엔티티 객체로 변환하는 메서드
	public Board toEntity() {
		return Board.builder()
				.id(id)
				.title(title)
				.content(content)
				.registerId(registerId)
				.readCnt(readCnt)
				.build();
	}

}
