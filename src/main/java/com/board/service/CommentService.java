package com.board.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import com.board.dto.comment.CommentRequestDto;
import com.board.dto.comment.CommentResponseDto;
import com.board.entity.comment.CommentEntity;
import com.board.entity.comment.CommentRepository;

import lombok.RequiredArgsConstructor;

/**
 * 게시글 댓글 관리를 위한 서비스 클래스
 */
@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;

    /**
     * 댓글 생성 메소드
     * 
     * @param commentRequestDto 생성할 댓글 정보가 담긴 DTO
     * @return 생성된 댓글의 ID
     */
    @Transactional
    public Long createComment(CommentRequestDto commentRequestDto) {
        CommentEntity comment = commentRequestDto.toEntity();
        return commentRepository.save(comment).getId();
    }

    /**
     * 주어진 댓글 ID로 댓글을 조회하는 메소드
     * 
     * @param commentId 조회할 댓글의 ID
     * @return 조회된 댓글 엔티티
     * @throws NoSuchElementException 주어진 ID에 해당하는 댓글이 없을 경우 발생
     */
    public CommentEntity getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Could not find comment with ID: " + commentId));
    }

    /**
     * 댓글을 수정하는 메소드
     * 
     * @param commentId 수정할 댓글의 ID
     * @param updatedComment 업데이트된 댓글 엔티티
     * @return 업데이트된 댓글 엔티티
     */
    @Transactional
    public CommentEntity updateComment(Long commentId, CommentEntity updatedComment) {
        CommentEntity comment = getCommentById(commentId);
        comment.setContent(updatedComment.getContent());
        return comment;
    }

    /**
     * 댓글을 삭제하는 메소드
     * 
     * @param commentId 삭제할 댓글의 ID
     */
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
    
    /**
     * 주어진 부모 댓글 ID에 대한 대댓글들을 찾는 메소드
     * 
     * @param parentId 부모 댓글의 ID
     * @return 대댓글 엔티티들의 리스트
     */
    public List<CommentEntity> findRepliesByParentId(Long parentId) {
        return commentRepository.findByParentId(parentId);
    }
    
    /**
     * 주어진 게시글 ID에 달린 댓글들을 조회하는 메소드
     * 
     * @param boardId 게시글의 ID
     * @return 댓글들의 DTO 리스트
     */
    @Transactional
    public List<CommentResponseDto> getCommentsByBoardId(Long boardId) {
        List<CommentEntity> commentEntities = commentRepository.findByBoardId(boardId, Sort.by(Sort.Direction.DESC, "registerTime"));
        return commentEntities.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
    
    /**
     * 주어진 게시글 ID에 달린 댓글 수를 조회하는 메소드
     * 
     * @param boardId 게시글의 ID
     * @return 댓글 수
     */
    public Long countCommentsByBoardId(Long boardId) {
        return commentRepository.countCommentsByBoardId(boardId);
    }
    
    /**
     * 주어진 게시글 ID에 달린 댓글 엔티티들을 조회하는 메소드
     * 
     * @param boardId 게시글의 ID
     * @return 댓글 엔티티들의 리스트
     */
    public List<CommentEntity> getCommentEntitiesByBoardId(Long boardId) {
        return commentRepository.findByBoardId(boardId, Sort.by(Sort.Direction.DESC, "registerTime"));
    }
}
