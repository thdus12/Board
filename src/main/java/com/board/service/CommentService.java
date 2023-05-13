package com.board.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import com.board.dto.comment.CommentRequestDto;
import com.board.dto.comment.CommentResponseDto;
import com.board.entity.comment.CommentEntity;
import com.board.entity.comment.CommentRepository;

@Service
public class CommentService {

	private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // 댓글 생성
    @Transactional
    public Long createComment(CommentRequestDto commentRequestDto) {
        CommentEntity comment = commentRequestDto.toEntity();
        return commentRepository.save(comment).getId();
    }

    // 댓글 조회
    public CommentEntity getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Could not find comment with ID: " + commentId));
    }

    // 댓글 수정
    @Transactional
    public CommentEntity updateComment(Long commentId, CommentEntity updatedComment) {
        CommentEntity comment = getCommentById(commentId);
        comment.setContent(updatedComment.getContent());
        return comment;
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
    
    // 댓글의 대댓글 찾기
    public List<CommentEntity> findRepliesByParentId(Long parentId) {
        return commentRepository.findByParentId(parentId);
    }
    
    // 게시글에 달린 댓글 조회
    @Transactional
    public List<CommentResponseDto> getCommentsByBoardId(Long boardId) {
        List<CommentEntity> commentEntities = commentRepository.findByBoardId(boardId, Sort.by(Sort.Direction.DESC, "registerTime"));
        return commentEntities.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
    
    // 게시글에 달린 댓글 수 조회
    public Long countCommentsByBoardId(Long boardId) {
        return commentRepository.countCommentsByBoardId(boardId);
    }
    
    public List<CommentEntity> getCommentEntitiesByBoardId(Long boardId) {
        return commentRepository.findByBoardId(boardId, Sort.by(Sort.Direction.DESC, "registerTime"));
    }
}