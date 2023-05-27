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
import com.board.entity.member.MemberEntity;
import com.board.service.BoardService;
import com.board.service.CommentService;
import com.board.service.member.MemberServiceImpl;

import lombok.RequiredArgsConstructor;

/**
 * 댓글과 대댓글에 대한 처리를 담당하는 컨트롤러
 */
@RequiredArgsConstructor
@Controller
public class CommentController {
	@Autowired
    private CommentService commentService;
	@Autowired
	private MemberServiceImpl memberService;
	@Autowired
	private BoardService boardService;
	
	 /**
     * 댓글을 수정하는 액션을 처리
     *
     * @param commentId 수정할 댓글의 ID
     * @param boardId 댓글이 속한 게시판의 ID
     * @param commentRequestDto 댓글 수정 정보를 담은 DTO
     * @return 게시판 보기 페이지 리다이렉트 경로
     */
	@PostMapping("/board/view/comment/update")
	public String updateComment(@RequestParam("commentId") Long commentId,
	                            @RequestParam("boardId") Long boardId,
	                            @ModelAttribute("comment") CommentRequestDto commentRequestDto) {
	    CommentEntity updatedComment = commentRequestDto.toEntity();
	    commentService.updateComment(commentId, updatedComment);
	    return "redirect:/board/view?id=" + boardId;
	}
	
	/**
     * 댓글을 삭제하는 액션을 처리
     *
     * @param commentId 삭제할 댓글의 ID
     * @param boardId 댓글이 속한 게시판의 ID
     * @return 게시판 보기 페이지 리다이렉트 경로
     */
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
	
	 /**
     * 새 댓글을 추가하는 액션을 처리
     *
     * @param boardId 댓글을 추가할 게시판의 ID
     * @param registerId 댓글 작성자의 ID
     * @param commentRequestDto 댓글 정보를 담은 DTO
     * @return 게시판 보기 페이지 리다이렉트 경로
     * @throws Exception 처리 중 발생한 예외
     */
	@PostMapping("/board/view/comment")
    public String addComment(@RequestParam("boardId") Long boardId, 
    						 @RequestParam("registerId") String registerId,
    						 CommentRequestDto commentRequestDto) throws Exception {
		try {
			BoardEntity board = boardService.getBoardById(boardId);
            MemberEntity member = memberService.getMemberByEmail(registerId);
            commentRequestDto.setMember(member);
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
	
	/**
     * 대댓글을 수정하는 액션을 처리
     *
     * @param replyId 수정할 대댓글의 ID
     * @param boardId 대댓글이 속한 게시판의 ID
     * @param commentRequestDto 대댓글 수정 정보를 담은 DTO
     * @return 게시판 보기 페이지 리다이렉트 경로
     */
	@PostMapping("/board/view/reply/update")
	public String updateReply(@RequestParam("replyId") Long replyId,
	                            @RequestParam("boardId") Long boardId,
	                            @ModelAttribute("comment") CommentRequestDto commentRequestDto) {
	    CommentEntity updatedComment = commentRequestDto.toEntity();
	    commentService.updateComment(replyId, updatedComment);
	    return "redirect:/board/view?id=" + boardId;
	}
	
	 /**
     * 대댓글을 삭제하는 액션을 처리
     *
     * @param replyId 삭제할 대댓글의 ID
     * @param boardId 대댓글이 속한 게시판의 ID
     * @return 게시판 보기 페이지 리다이렉트 경로
     */
	@PostMapping("/board/view/reply/delete")
	public String deleteRelply(@RequestParam("replyId") Long replyId,
	                            @RequestParam("boardId") Long boardId) {
	    // 댓글 삭제
	    commentService.deleteComment(replyId);
	    
	    return "redirect:/board/view?id=" + boardId;
	}
	
	/**
     * 새 대댓글을 추가하는 액션을 처리
     *
     * @param parentId 대댓글의 부모 댓글 ID
     * @param depth 대댓글의 깊이
     * @param boardId 대댓글을 추가할 게시판의 ID
     * @param registerId 대댓글 작성자의 ID
     * @param commentRequestDto 대댓글 정보를 담은 DTO
     * @return 게시판 보기 페이지 리다이렉트 경로
     * @throws Exception 처리 중 발생한 예외
     */
	@PostMapping("/board/view/reply")
    public String addReply(@RequestParam("parentId") Long parentId,
    					   @RequestParam("depth") Long depth,
    					   @RequestParam("boardId") Long boardId, 
    					   @RequestParam("registerId") String registerId, 
    					   CommentRequestDto commentRequestDto) throws Exception {
		try {			
			BoardEntity board = boardService.getBoardById(boardId);
			MemberEntity member = memberService.getMemberByEmail(registerId);
            commentRequestDto.setMember(member);
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
