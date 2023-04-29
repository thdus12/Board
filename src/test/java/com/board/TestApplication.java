package com.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.board.dto.board.BoardRequestDto;
import com.board.dto.board.BoardResponseDto;
import com.board.service.BoardService;

@SpringBootTest
class TestApplication {
	
	@Autowired
	private BoardService boardService;

	// 게시판에 새로운 게시글을 저장하고 저장된 게시글을 조회하는 테스트 메소드
    @Test
    void save() {
        BoardRequestDto boardSaveDto = new BoardRequestDto();

        boardSaveDto.setTitle("제목입니다.");
        boardSaveDto.setContent("내용입니다.");
        boardSaveDto.setRegisterId("작성자");

        Long result = boardService.save(boardSaveDto);

        if (result > 0) {
            System.out.println("# Success save() ~");
            findAll();
            findById(result);
        } else {
            System.out.println("# Fail Save() ~");
        }
    }

    // 게시판에 저장된 모든 게시글을 조회하는 테스트 메소드
    void findAll() {
//        List<BoardResponseDto> list = boardService.findAll(0,100);
//
//        if (list != null) {
//            System.out.println("# Success findAll() : " + list.toString());
//        } else {
//            System.out.println("# Fail findAll() ~");
//        }
    }

    // 주어진 ID를 가진 게시글을 조회하고 업데이트를 시도하는 테스트 메소드
    void findById(Long id) {
//        BoardResponseDto info = boardService.findById(id);
//
//        if (info != null) {
//            System.out.println("# Success findById() : " + info.toString());
//            updateBoard(id);
//        } else {
//            System.out.println("# Fail findById() ~");
//        }
    }

    // 게시글을 수정하고 수정된 게시글을 저장하는 테스트 메소드
//    void updateBoard(Long id) {
//
//        BoardRequestDto boardRequestDto = new BoardRequestDto();
//
//        boardRequestDto.setId(id);
//        boardRequestDto.setTitle("업데이트 제목");
//        boardRequestDto.setContent("업데이트 내용");
//        boardRequestDto.setRegisterId("작성자");
//
//        int result = boardService.updateBoard(boardRequestDto);
//
//        if (result > 0) {
//            System.out.println("# Success updateBoard() ~");
//        } else {
//            System.out.println("# Fail updateBoard() ~");
//        }
//    }

}
