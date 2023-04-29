package com.board.web;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.board.dto.board.BoardRequestDto;
import com.board.service.BoardService;
import lombok.RequiredArgsConstructor;

// 생성자를 자동으로 생성하는 Lombok 어노테이션
@RequiredArgsConstructor
// 이 클래스를 스프링 MVC의 컨트롤러로 사용하도록 하는 어노테이션
@Controller
public class BoardController {

    // BoardService를 주입 받음
    private final BoardService boardService;

    // 게시글 목록 페이지를 반환하는 메소드
    @GetMapping("/board/list")
	public String getBoardListPage(Model model
			, @RequestParam(required = false, defaultValue = "0") Integer page
			, @RequestParam(required = false, defaultValue = "5") Integer size) throws Exception {
		
		try {
			model.addAttribute("resultMap", boardService.findAll(page, size));
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "/board/list";
	}

    // 게시글 작성 페이지를 반환하는 메소드
    @GetMapping("/board/write")
    public String getBoardWritePage(Model model, BoardRequestDto boardRequestDto) {
    	System.out.println("asdddd");
    	return "/board/write";
    }

    // 게시글 조회 페이지를 반환하는 메소드
    @GetMapping("/board/view")
	public String getBoardViewPage(Model model, BoardRequestDto boardRequestDto) throws Exception {
    	
    	boardService.updateBoardReadCntInc(boardRequestDto.getId());
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (boardRequestDto.getId() != null) {
				resultMap.put("info", boardService.findById(boardRequestDto.getId()));
				model.addAttribute("resultMap", resultMap);
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "board/view";
	}

    // 게시글 작성을 처리하고 게시글 목록 페이지로 리다이렉트하는 메소드
    @PostMapping("/board/write/action")
	public String boardWriteAction(Model model, BoardRequestDto boardRequestDto) throws Exception {
    	System.out.println("asd");
    	System.out.println(boardRequestDto.toString());
		
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
    
    @PostMapping("/board/view/action")
	public String boardViewAction(Model model, BoardRequestDto boardRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		
		try {
			boardService.updateBoard(boardRequestDto);
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/board/list";
	}
    
    @PostMapping("/board/view/delete")
	public String boardViewDeleteAction(Model model, @RequestParam() Long id) throws Exception {
		
		try {
			boardService.deleteById(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/board/list";
	}
	
	@PostMapping("/board/delete")
	public String boardDeleteAction(Model model, @RequestParam() Long[] deleteId) throws Exception {
		
		try {
			boardService.deleteAll(deleteId);
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/board/list";
	}
}