package com.board.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.dto.comment.CommentRequestDto;
import com.board.entity.board.BoardEntity;
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

    // 게시글의 모든 댓글 조회
    public List<CommentEntity> getCommentsByBoardId(Long boardId) {
        return commentRepository.findByBoardId(boardId);
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
}