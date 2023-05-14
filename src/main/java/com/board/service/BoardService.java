package com.board.service;

import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import com.board.dto.board.BoardRequestDto;
import com.board.dto.board.BoardResponseDto;
import com.board.entity.board.BoardEntity;
import com.board.entity.board.BoardRepository;

// 생성자를 자동으로 생성하는 Lombok 어노테이션
@RequiredArgsConstructor
// 이 클래스를 스프링 서비스로 사용하도록 하는 어노테이션
@Service
public class BoardService {	
	@Autowired	
    private final BoardRepository boardRepository;
	@Autowired
	private final CommentService commentService;

    // 게시글 저장 메소드
    @Transactional
    public Long save(BoardRequestDto boardRequestDto) {
        return boardRepository.save(boardRequestDto.toEntity()).getId();
    }
    
    /*
		트랜잭션에 readOnly=true 옵션을 주면 스프링 프레임워크가 하이버네이트 세션 플러시 모드를 MANUAL로 설정한다.
		이렇게 하면 강제로 플러시를 호출하지 않는 한 플러시가 일어나지 않는다.
		따라서 트랜잭션을 커밋하더라도 영속성 컨텍스트가 플러시 되지 않아서 엔티티의 등록, 수정, 삭제이 동작하지 않고,
		또한 읽기 전용으로, 영속성 컨텍스트는 변경 감지를 위한 스냅샷을 보관하지 않으므로 성능이 향상된다.
	*/

    // 게시글 목록 조회 메소드
    @Transactional(readOnly = true)
    public HashMap<String, Object> findAll(Integer page, Integer size) {
    	
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        Page<BoardEntity> list = boardRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));

        resultMap.put("list", list.stream().map(board -> {
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            boardResponseDto.setCommentCount(commentService.countCommentsByBoardId(board.getId())); // 댓글 수를 게시글에 추가
            boardResponseDto.setUpvoteCount(getUpvoteCount(board.getId()));
            return boardResponseDto;
        }).collect(Collectors.toList()));
        resultMap.put("paging", list.getPageable());
        resultMap.put("totalCnt", list.getTotalElements());
        resultMap.put("totalPage", list.getTotalPages());
        
        return resultMap;
    }

    // 게시글 조회 메소드
    public BoardResponseDto findById(Long id) {
        return new BoardResponseDto(boardRepository.findById(id).get());
    }

    // 게시글 업데이트 메소드
    public int updateBoard(BoardRequestDto boardRequestDto) {
		return boardRepository.updateBoard(boardRequestDto);
	}

    // 게시글 조회수 증가 메소드
    public int updateBoardReadCntInc(Long id) {
        return boardRepository.updateBoardReadCntInc(id);
    }

    // 게시글 삭제 메소드
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }
    
    // 게시글 다중 삭제 메소드
    public void deleteAll(Long[] deleteId) {
    	boardRepository.deleteBoard(deleteId);
    }

    public BoardEntity getBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Could not find board with ID: " + id));
    }  
    
    // 게시글 추천 메소드
    public void updateUpvote(Long id) {
        boardRepository.updateUpvote(id);
    }

    // 게시글 비추천 메소드
    public void updateDownvote(Long id) {
        boardRepository.updateDownvote(id);
    }
    
    // 게시글 추천수 가져오는 메소드
	public int getUpvoteCount(Long boardId) {
		BoardEntity board = boardRepository.findById(boardId).orElse(null);
	    if (board != null) {
	        return board.getUpvoteCount();
	    } else {
	        return 0;
	    }
	}

	public int getDownvoteCount(Long boardId) {
		BoardEntity board = boardRepository.findById(boardId).orElse(null);
	    if (board != null) {
	        return board.getDownvoteCount();
	    } else {
	        return 0;
	    }
	}
}