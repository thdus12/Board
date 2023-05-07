package com.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.dto.comment.CommentRequestDto;
import com.board.dto.comment.CommentResponseDto;
import com.board.entity.board.BoardEntity;
import com.board.entity.comment.CommentEntity;
import com.board.service.BoardService;
import com.board.service.CommentService;

import lombok.RequiredArgsConstructor;

//생성자를 자동으로 생성하는 Lombok 어노테이션
@RequiredArgsConstructor
//이 클래스를 스프링 MVC의 컨트롤러로 사용하도록 하는 어노테이션
@Controller
public class CommentController {
	
	@Autowired
    private CommentService commentService;
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/board/view/comment")
    public String addComment(@RequestParam("boardId") Long boardId,@RequestParam("registerId") String registerId, CommentRequestDto commentRequestDto) throws Exception {
		try {
			BoardEntity board = boardService.getBoardById(boardId);
	        commentRequestDto.setBoard(board);
	        commentRequestDto.setRegisterId(registerId);
	        commentRequestDto.setParentId((long) 0);
	        commentRequestDto.setDepth((long) 1);
	        Long result = commentService.createComment(commentRequestDto);
	        
			if (result < 0) {
				throw new Exception("#Exception comment!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
        return "redirect:/board/view?id=" + boardId;		
    }
}
