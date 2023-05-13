package com.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.dto.comment.CommentRequestDto;
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
	
	// 댓글 수정
	@PostMapping("/board/view/comment/update")
	public String updateComment(@RequestParam("commentId") Long commentId,
	                            @RequestParam("boardId") Long boardId,
	                            @ModelAttribute("comment") CommentRequestDto commentRequestDto) {
	    CommentEntity updatedComment = commentRequestDto.toEntity();
	    commentService.updateComment(commentId, updatedComment);
	    return "redirect:/board/view?id=" + boardId;
	}
	
	// 댓글 삭제
	@PostMapping("/board/view/comment/delete")
	public String deleteComment(@RequestParam("commentId") Long commentId,
	                            @RequestParam("boardId") Long boardId) {
		// 댓글의 대댓글 찾기
	    List<CommentEntity> replies = commentService.findRepliesByParentId(commentId);

	    // 대댓글 삭제
	    for (CommentEntity reply : replies) {
	        commentService.deleteComment(reply.getId());
	    }

	    // 댓글 삭제
	    commentService.deleteComment(commentId);
	    
	    return "redirect:/board/view?id=" + boardId;
	}
	
	// 댓글 추가
	@PostMapping("/board/view/comment")
    public String addComment(@RequestParam("boardId") Long boardId, @RequestParam("registerId") String registerId, CommentRequestDto commentRequestDto) throws Exception {
		try {
			BoardEntity board = boardService.getBoardById(boardId);
	        commentRequestDto.setBoard(board);
	        commentRequestDto.setRegisterId(registerId);
	        commentRequestDto.setParentId((long) 0);
	        commentRequestDto.setDepth((long) 0);
	        Long result = commentService.createComment(commentRequestDto);
	        
			if (result < 0) {
				throw new Exception("#Exception comment!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
        return "redirect:/board/view?id=" + boardId;		
    }
	
	// 대댓글 수정
	@PostMapping("/board/view/reply/update")
	public String updateReply(@RequestParam("replyId") Long replyId,
	                            @RequestParam("boardId") Long boardId,
	                            @ModelAttribute("comment") CommentRequestDto commentRequestDto) {
	    CommentEntity updatedComment = commentRequestDto.toEntity();
	    commentService.updateComment(replyId, updatedComment);
	    return "redirect:/board/view?id=" + boardId;
	}
	
	// 대댓글 삭제
	@PostMapping("/board/view/reply/delete")
	public String deleteRelply(@RequestParam("replyId") Long replyId,
	                            @RequestParam("boardId") Long boardId) {
	    // 댓글 삭제
	    commentService.deleteComment(replyId);
	    
	    return "redirect:/board/view?id=" + boardId;
	}
	
	// 대댓글 추가
	@PostMapping("/board/view/reply")
    public String addReply(@RequestParam("parentId") Long parentId,
    					   @RequestParam("depth") Long depth,
    					   @RequestParam("boardId") Long boardId, 
    					   @RequestParam("registerId") String registerId, 
    					   CommentRequestDto commentRequestDto) throws Exception {
		try {			
			BoardEntity board = boardService.getBoardById(boardId);
	        commentRequestDto.setBoard(board);
	        commentRequestDto.setRegisterId(registerId);
	        commentRequestDto.setParentId(parentId);
	        commentRequestDto.setDepth(depth);
	        Long result = commentService.createComment(commentRequestDto);
	        
			if (result < 0) {
				throw new Exception("#Exception reply!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
        return "redirect:/board/view?id=" + boardId;		
    }
}
