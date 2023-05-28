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
import com.board.dto.board.BoardResponseDto;
import com.board.dto.comment.CommentResponseDto;
import com.board.entity.board.BoardEntity;
import com.board.entity.comment.CommentEntity;
import com.board.entity.member.MemberEntity;
import com.board.service.BoardFileService;
import com.board.service.BoardService;
import com.board.service.CommentService;
import com.board.service.member.MemberServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

/**
 * 게시글과 관련된 동작을 처리하는 컨트롤러
 */
@RequiredArgsConstructor
@Controller
public class BoardController {
	@Autowired
    private BoardService boardService;	
	@Autowired
    private CommentService commentService;	
	@Autowired
    private MemberServiceImpl memberService;	
	@Autowired
	private BoardFileService boardFileService;
	
	/**
     * 현재 인증된 사용자의 이메일을 반환
     *
     * @return 사용자 이메일
     */
	private String getAuthenticatedUserEmail() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return authentication.getName();
	}
    
	 /**
     * 게시글 리스트 페이지를 반환
     *
     * @param model Spring Model 객체
     * @param page 페이지 번호, 필수가 아님 (기본값 = 0)
     * @param size 페이지당 게시글 개수, 필수가 아님 (기본값 = 5)
     * @return 게시글 리스트 페이지 경로
     * @throws Exception 처리 중 발생한 예외
     */
    @GetMapping("board/list")
	public String getBoardListPage(Model model
			, @RequestParam(required = false, defaultValue = "0") Integer page
			, @RequestParam(required = false, defaultValue = "5") Integer size) throws Exception {
		
		try {			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        boolean isAdmin = memberService.isAdmin(auth.getName());
	        model.addAttribute("isAdmin", isAdmin);
			
			String userEmail = getAuthenticatedUserEmail();
	        model.addAttribute("userEmail", userEmail);
			
	        HashMap<String, Object> resultMap = boardService.findAll(page, size);
	        model.addAttribute("resultMap", resultMap);
	        
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "board/list";
	}

    /**
     * 게시글 작성 페이지를 반환
     *
     * @param model Spring Model 객체
     * @return 게시글 작성 페이지 경로
     */
    @GetMapping("/board/write")
    public String getBoardWritePage(Model model) {
    	String userEmail = getAuthenticatedUserEmail();
        model.addAttribute("userEmail", userEmail);
    	
    	return "board/write";
    }
    
    /**
     * 게시글 작성 액션을 처리
     *
     * @param model Spring Model 객체
     * @param memberId 회원 ID
     * @param boardRequestDto 게시글 요청 DTO
     * @param multiRequest MultipartHttpServletRequest 객체
     * @return 게시글 리스트 페이지 리다이렉트 경로
     * @throws Exception 처리 중 발생한 예외
     */
    @PostMapping("/board/write/action")
    public String boardWriteAction(Model model,
    							   @RequestParam("memberId") String memberId,
                                   BoardRequestDto boardRequestDto,
                                   MultipartHttpServletRequest multiRequest) throws Exception {
        try {
            // 현재 로그인한 회원의 정보를 가져옵니다.
            MemberEntity member = memberService.getMemberByEmail(memberId);
            boardRequestDto.setMember(member);

            Long result = boardService.save(boardRequestDto);

            if (result < 0) {
                throw new Exception("#Exception boardWriteAction!");
            }
            
            BoardEntity board = boardService.getBoardById(result);
            boardFileService.uploadFile(multiRequest, board);
            
        } catch (Exception e) {
            throw new Exception(e.getMessage()); 
        }
        return "redirect:/board/list";
    }

    /**
     * 게시글 보기 페이지를 반환
     *
     * @param model Spring Model 객체
     * @param boardRequestDto 게시글 요청 DTO
     * @return 게시글 보기 페이지 경로
     * @throws Exception 처리 중 발생한 예외
     */
    @GetMapping("/board/view")
	public String getBoardViewPage(Model model, 
								   BoardRequestDto boardRequestDto) throws Exception {
    	Long boardId = boardRequestDto.getId();
    	
    	// 게시글 조회수 증가
    	boardService.updateBoardReadCntInc(boardId);		
		
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			if (boardRequestDto.getId() != null) {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		        boolean isAdmin = memberService.isAdmin(auth.getName());
		        model.addAttribute("isAdmin", isAdmin);
				
				String userEmail = getAuthenticatedUserEmail();
		        model.addAttribute("userEmail", userEmail);
				
		        BoardResponseDto info = boardService.findById(boardRequestDto.getId());
		        List <Long> fileList = boardFileService.findByBoardFileId(info.getId());
		        resultMap.put("info", info);
		        resultMap.put("fileList", fileList);
				model.addAttribute("resultMap", resultMap);
				
				List<CommentResponseDto> commentList = commentService.getCommentsByBoardId(boardRequestDto.getId());
	            model.addAttribute("commentList", commentList);  
	            
	            Long commentCount = commentService.countCommentsByBoardId(boardId);
	            model.addAttribute("commentCount", commentCount);
	            
	            int upvoteCount = boardService.getUpvoteCount(boardId);
	            model.addAttribute("upvoteCount", upvoteCount);
	            
	            int downvoteCount = boardService.getDownvoteCount(boardId);
	            model.addAttribute("downvoteCount", downvoteCount);
	            
			}
		} catch (Exception e) {
			System.out.println(e);
		    model.addAttribute("errorMessage", e.getMessage());
		    return "error";
		}
		
		return "board/view";
	}
    
    /**
     * 게시글 수정 페이지를 반환
     *
     * @param model Spring Model 객체
     * @param request HttpServletRequest 객체
     * @return 게시글 수정 페이지 경로
     * @throws Exception 처리 중 발생한 예외
     */
    @GetMapping("/board/edit")
    public String getBoardEditPage(Model model, 
    							   HttpServletRequest request) throws Exception {

        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String idStr = request.getParameter("id");
            if (idStr != null) {
                Long id = Long.parseLong(idStr);
                BoardResponseDto info = boardService.findById(id);
                List <Long> fileList = boardFileService.findByBoardFileId(info.getId());
		        resultMap.put("info", info);
		        resultMap.put("fileList", fileList);
				model.addAttribute("resultMap", resultMap);
            } 
        } catch (Exception e) {
            throw new Exception(e.getMessage()); 
        }

        return "board/edit";
    }
    
    /**
     * 게시글 수정 액션을 처리
     *
     * @param model Spring Model 객체
     * @param deletedFileIdsJson 삭제된 파일의 ID 리스트 JSON 문자열
     * @param boardRequestDto 게시글 요청 DTO
     * @param multiRequest MultipartHttpServletRequest 객체
     * @return 게시글 보기 페이지 리다이렉트 경로
     * @throws Exception 처리 중 발생한 예외
     */
    @PostMapping("/board/edit/action")
    public String boardEditAction(Model model,
                                  @RequestParam(value = "deletedFileIds", required = false) String deletedFileIdsJson,
                                  BoardRequestDto boardRequestDto, 
                                  MultipartHttpServletRequest multiRequest) throws Exception {
    	Long id = boardRequestDto.getId();
        try {
            boardService.updateBoard(boardRequestDto);
            if (deletedFileIdsJson != null && !deletedFileIdsJson.isEmpty()) {
                Long[] deletedFileIds = new ObjectMapper().readValue(deletedFileIdsJson, Long[].class);
                boardFileService.updateDeleteYn(deletedFileIds);
            }
            
            BoardEntity board = boardService.getBoardById(id);
            boardFileService.uploadFile(multiRequest, board);
            
        } catch (Exception e) {
            throw new Exception(e.getMessage()); 
        }
        return "redirect:/board/view?id=" + id;
    }
    
    /**
     * 게시글 상세보기에서 삭제 액션을 처리
     *
     * @param model Spring Model 객체
     * @param id 삭제할 게시글 ID
     * @return 게시글 리스트 페이지 리다이렉트 경로
     * @throws Exception 처리 중 발생한 예외
     */
    @PostMapping("/board/view/delete")
	public String boardViewDeleteAction(Model model, 
										@RequestParam() Long id) throws Exception {
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

	        // 첨부 파일 삭제
	        boardFileService.deleteByBoardId(id);
	        
	        // 게시글 삭제
	        boardService.deleteById(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/board/list";
	}
	
    /**
     * 게시글 다중 삭제 액션을 처리
     *
     * @param model Spring Model 객체
     * @param deleteId 삭제할 게시글 ID 배열
     * @return 게시글 리스트 페이지 리다이렉트 경로
     * @throws Exception 처리 중 발생한 예외
     */
	@PostMapping("/board/delete")
	public String boardDeleteAction(Model model, 
									@RequestParam() Long[] deleteId) throws Exception {	
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
	            // 첨부파일 삭제
	            boardFileService.deleteByBoardId(id);
	        }
	        // 다중 게시글 삭제
	        boardService.deleteAll(deleteId);
	        
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/board/list";
	}
	
	/**
     * 게시글 추천수를 증가
     *
     * @param id 추천을 증가시킬 게시글 ID
     * @return 게시글 보기 페이지 리다이렉트 경로
     */
	@PostMapping("/board/view/updateUpvote")
	public String updateuUpvote(@RequestParam("id") Long id) {
	    boardService.updateUpvote(id);
	    return "redirect:/board/view?id=" + id;
	}

	/**
     * 게시글 반대수를 증가
     *
     * @param id 반대를 증가시킬 게시글 ID
     * @return 게시글 보기 페이지 리다이렉트 경로
     */
	@PostMapping("/board/view/updateDownvote")
	public String updateDownvote(@RequestParam("id") Long id) {
	    boardService.updateDownvote(id);
	    return "redirect:/board/view?id=" + id;
	}	
	
	/**
     * 게시글 추천수를 취소
     *
     * @param id 추천을 취소할 게시글 ID
     * @return 게시글 보기 페이지 리다이렉트 경로
     */
	@PostMapping("/board/view/cancelUpvote")
	public String cancelUpvote(@RequestParam("id") Long id) {
	    boardService.cancelUpvote(id);
	    return "redirect:/board/view?id=" + id;
	}

	/**
     * 게시글 반대수를 취소
     *
     * @param id 반대를 취소할 게시글 ID
     * @return 게시글 보기 페이지 리다이렉트 경로
     */
	@PostMapping("/board/view/cancelDownvote")
	public String cancelDownvote(@RequestParam("id") Long id) {
	    boardService.cancelDownvote(id);
	    return "redirect:/board/view?id=" + id;
	}	
}