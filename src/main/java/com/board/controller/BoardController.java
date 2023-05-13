package com.board.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.board.dto.board.BoardRequestDto;
import com.board.dto.comment.CommentResponseDto;
import com.board.entity.comment.CommentEntity;
import com.board.service.BoardService;
import com.board.service.CommentService;

import lombok.RequiredArgsConstructor;

// 생성자를 자동으로 생성하는 Lombok 어노테이션
@RequiredArgsConstructor
// 이 클래스를 스프링 MVC의 컨트롤러로 사용하도록 하는 어노테이션
@Controller
public class BoardController {

    // BoardService를 주입 받음
	@Autowired
    private BoardService boardService;
	
	@Autowired
    private CommentService commentService;
	
	private String getAuthenticatedUserEmail() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return authentication.getName();
	}
    
    // 게시글 목록 페이지를 반환하는 메소드
    @GetMapping("/board/list")
	public String getBoardListPage(Model model
			, @RequestParam(required = false, defaultValue = "0") Integer page
			, @RequestParam(required = false, defaultValue = "5") Integer size) throws Exception {
		
		try {
			String userEmail = getAuthenticatedUserEmail();
	        model.addAttribute("userEmail", userEmail);
			model.addAttribute("resultMap", boardService.findAll(page, size));
			
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "board/list";
	}

    // 게시글 작성 페이지를 반환하는 메소드
    @GetMapping("/board/write")
    public String getBoardWritePage(Model model) {
    	String userEmail = getAuthenticatedUserEmail();
        model.addAttribute("userEmail", userEmail);
    	
    	return "board/write";
    }

    // 게시글 조회 페이지를 반환하는 메소드
    @GetMapping("/board/view")
	public String getBoardViewPage(Model model, BoardRequestDto boardRequestDto) throws Exception {
    	Long boardId = boardRequestDto.getId();
    	
    	// 게시글 조회수 증가
    	boardService.updateBoardReadCntInc(boardId);
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (boardRequestDto.getId() != null) {
				String userEmail = getAuthenticatedUserEmail();
		        model.addAttribute("userEmail", userEmail);
				
		        resultMap.put("info", boardService.findById(boardRequestDto.getId()));
				model.addAttribute("resultMap", resultMap);
				
				List<CommentResponseDto> commentList = commentService.getCommentsByBoardId(boardRequestDto.getId());
	            model.addAttribute("commentList", commentList);   
	            
	            int upvoteCount = boardService.getUpvoteCount(boardId);
	            model.addAttribute("upvoteCount", upvoteCount);
	            
	            int downvoteCount = boardService.getDownvoteCount(boardId);
	            model.addAttribute("downvoteCount", downvoteCount);
	            
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "board/view";
	}
    
    // 게시글 수정 페이지를 반환하는 메소드
    @GetMapping("/board/edit")
    public String getBoardEditPage(Model model, HttpServletRequest request) throws Exception {

        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String idStr = request.getParameter("id");
            if (idStr != null) {
                Long id = Long.parseLong(idStr);
                resultMap.put("info", boardService.findById(id));
                model.addAttribute("resultMap", resultMap);
            } 
        } catch (Exception e) {
            throw new Exception(e.getMessage()); 
        }

        return "board/edit";
    }
        
    // 게시글 작성을 처리하고 게시글 목록 페이지로 리다이렉트하는 메소드
    @PostMapping("/board/write/action")
	public String boardWriteAction(Model model, BoardRequestDto boardRequestDto) throws Exception {
		
		try {
			Long result = boardService.save(boardRequestDto);
			
			if (result < 0) {
				throw new Exception("#Exception boardWriteAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/board/list";
	}
    
    // 게시글 수정 후 저장하는 메소드
    @PostMapping("/board/edit/action")
	public String boardViewAction(Model model, BoardRequestDto boardRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		
		try {
			boardService.updateBoard(boardRequestDto);
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/board/list";
	}
    
 // view.html에서 게시글 삭제
    @PostMapping("/board/view/delete")
	public String boardViewDeleteAction(Model model, @RequestParam() Long id) throws Exception {
		try {
			// 댓글 및 대댓글 삭제
	        List<CommentEntity> comments = commentService.getCommentEntitiesByBoardId(id);
	        for (CommentEntity comment : comments) {
	            List<CommentEntity> replies = commentService.findRepliesByParentId(comment.getId());
	            for (CommentEntity reply : replies) {
	                commentService.deleteComment(reply.getId());
	            }
	            commentService.deleteComment(comment.getId());
	        }

	        // 게시글 삭제
	        boardService.deleteById(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/board/list";
	}
	
    // 게시글 다중 삭제
	@PostMapping("/board/delete")
	public String boardDeleteAction(Model model, @RequestParam() Long[] deleteId) throws Exception {	
		try {
			for (Long id : deleteId) {
	            // 댓글 및 대댓글 삭제
	            List<CommentEntity> comments = commentService.getCommentEntitiesByBoardId(id);
	            for (CommentEntity comment : comments) {
	                List<CommentEntity> replies = commentService.findRepliesByParentId(comment.getId());
	                for (CommentEntity reply : replies) {
	                    commentService.deleteComment(reply.getId());
	                }
	                commentService.deleteComment(comment.getId());
	            }
	        }
	        // 다중 게시글 삭제
	        boardService.deleteAll(deleteId);
	        
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/board/list";
	}
	
	// 게시글 추천수 업데이트 메소드
	@PostMapping("/board/view/updateUpvote")
	public String upvote(@RequestParam("id") Long id) {
	    boardService.updateUpvote(id);
	    return "redirect:/board/view?id=" + id;
	}

	// 게시글 비추천수 업데이트 메소드
	@PostMapping("/board/view/updateDownvote")
	public String downvote(@RequestParam("id") Long id) {
	    boardService.updateDownvote(id);
	    return "redirect:/board/view?id=" + id;
	}	
}