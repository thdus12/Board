package com.board.service;

import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import com.board.dto.board.BoardRequestDto;
import com.board.dto.board.BoardResponseDto;
import com.board.entity.board.BoardEntity;
import com.board.entity.board.BoardRepository;

/**
 * 게시글 관리를 위한 서비스 클래스
 */
@RequiredArgsConstructor
@Service
public class BoardService {	
    private final BoardRepository boardRepository;
    private final CommentService commentService;

    /**
     * 게시글을 저장하는 메소드
     * 
     * @param boardRequestDto 게시글 요청 DTO
     * @return 저장된 게시글의 ID
     */
    @Transactional
    public Long save(BoardRequestDto boardRequestDto) {
        return boardRepository.save(boardRequestDto.toEntity()).getId();
    }
    
    /**
     * 게시글 목록을 조회하는 메소드
     * 
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 게시글 목록과 페이징 정보를 담은 HashMap
     */
    @Transactional(readOnly = true)
    public HashMap<String, Object> findAll(Integer page, Integer size, String category) throws Exception {
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();

        Page<BoardEntity> list = boardRepository.findByCategoryName(PageRequest.of(page, size, Sort.by("isNotice").descending().and(Sort.by("id").descending())), category);
        
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

    /**
     * 게시글을 ID로 조회하는 메소드
     * 
     * @param id 게시글 ID
     * @return 게시글 응답 DTO
     */
    public BoardResponseDto findById(Long id) {
        return new BoardResponseDto(boardRepository.findById(id).get());
    }

    /**
     * 게시글을 업데이트하는 메소드
     * 
     * @param boardRequestDto 게시글 요청 DTO
     * @return 업데이트된 게시글의 개수
     */
    public int updateBoard(BoardRequestDto boardRequestDto) {
        return boardRepository.updateBoard(boardRequestDto);
    }

    /**
     * 게시글의 조회수를 증가시키는 메소드
     * 
     * @param id 게시글 ID
     * @return 업데이트된 게시글의 개수
     */
    public int updateBoardReadCntInc(Long id) {
        return boardRepository.updateBoardReadCntInc(id);
    }

    /**
     * 게시글을 삭제하는 메소드
     * 
     * @param id 게시글 ID
     */
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }
    
    /**
     * 여러 개의 게시글을 한 번에 삭제하는 메소드
     * 
     * @param deleteId 삭제할 게시글 ID 배열
     */
    public void deleteAll(Long[] deleteId) {
        boardRepository.deleteBoard(deleteId);
    }

    /**
     * 주어진 ID로 게시글을 찾는 메소드
     * 
     * @param id 게시글 ID
     * @return 게시글 엔티티
     * @throws NoSuchElementException 주어진 ID에 해당하는 게시글이 없을 경우 발생
     */
    public BoardEntity getBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Could not find board with ID: " + id));
    }  
    
    /**
     * 게시글의 추천 수를 증가시키는 메소드
     * 
     * @param id 게시글 ID
     */
    public void updateUpvote(Long id) {
        boardRepository.updateUpvote(id);
    }

    /**
     * 게시글의 비추천 수를 증가시키는 메소드
     * 
     * @param id 게시글 ID
     */
    public void updateDownvote(Long id) {
        boardRepository.updateDownvote(id);
    }
    
    /**
     * 게시글의 추천을 취소하는 메소드
     * 
     * @param id 게시글 ID
     */
    public void cancelUpvote(Long id) {
        boardRepository.cancelUpvote(id);
    }

    /**
     * 게시글의 비추천을 취소하는 메소드
     * 
     * @param id 게시글 ID
     */
    public void cancelDownvote(Long id) {
        boardRepository.cancelDownvote(id);
    }
    
    /**
     * 게시글의 추천 수를 가져오는 메소드
     * 
     * @param boardId 게시글 ID
     * @return 추천 수
     */
    public int getUpvoteCount(Long boardId) {
        BoardEntity board = boardRepository.findById(boardId).orElse(null);
        if (board != null) {
            return board.getUpvoteCount();
        } else {
            return 0;
        }
    }
    
    /**
     * 게시글의 비추천 수를 가져오는 메소드
     * 
     * @param boardId 게시글 ID
     * @return 비추천 수
     */
    public int getDownvoteCount(Long boardId) {
        BoardEntity board = boardRepository.findById(boardId).orElse(null);
        if (board != null) {
            return board.getDownvoteCount();
        } else {
            return 0;
        }
    }
    
    public List<BoardEntity> getAllBoards(String category) {
        return boardRepository.findByCategoryName(category);
    }
}
