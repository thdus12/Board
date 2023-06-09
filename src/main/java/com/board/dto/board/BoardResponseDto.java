package com.board.dto.board;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.board.entity.board.BoardEntity;
import com.board.entity.category.CategoryEntity;
import com.board.entity.member.MemberEntity;

import lombok.Getter;

/**
 * 게시판 게시글에 대한 정보를 응답하는 Data Transfer Object 클래스
 */
@Getter
public class BoardResponseDto {
	private Long id;  					 // 게시글의 ID
    private String title;  				 // 게시글의 제목
    private String content;  			 // 게시글의 내용
    private String registerId;  		 // 게시글을 등록한 사용자의 ID
    private int readCnt;  				 // 게시글의 조회 수
    private LocalDateTime registerTime;  // 게시글이 등록된 시간
    private Long commentCount;   		 // 게시글의 댓글 수
    private int upvoteCount;  	 		 // 게시글의 추천 수
    private MemberEntity member; 		 // 게시글을 작성한 회원 정보
    private int isNotice;    			 // 공지사항 여부 
    private CategoryEntity category; 	 // 카테고리 정보

    /**
     * BoardEntity 객체를 기반으로 BoardResponseDto 객체를 생성하는 생성자
     * 
     * @param board : 게시글의 정보를 담은 엔티티 객체
     */
    public BoardResponseDto(BoardEntity board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.readCnt = board.getReadCnt();
        this.registerId = board.getRegisterId();
        this.registerTime = board.getRegisterTime();
        this.member = board.getMember();
        this.isNotice = board.getIsNotice();
        this.category = board.getCategory();
    }
    
    /**
     * 게시글 등록 시간을 포맷팅하여 반환하는 메소드
     * 만약 게시글 등록 날짜가 현재 날짜와 동일하다면, 시간을 'HH:mm' 형태로 포맷팅하고,
     * 그렇지 않다면, 날짜를 'yyyy-MM-dd' 형태로 포맷팅하여 반환
     *
     * @return 포맷팅된 등록 시간 문자열
     */
    public String getFormattedRegisterTime() {
        LocalDateTime now = LocalDateTime.now();

        if (registerTime.toLocalDate().equals(now.toLocalDate())) {
            return registerTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        } else {
            return registerTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }

    /**
     * 댓글 수를 설정하는 메소드
     *
     * @param commentCount : 설정할 댓글 수
     */
    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
    
    /**
     * 게시글 추천 수를 설정하는 메소드
     *
     * @param upvoteCount : 설정할 추천 수
     */
    public void setUpvoteCount(int upvoteCount) {
        this.upvoteCount = upvoteCount;
    }

    /**
     * 이 객체의 필드를 모두 포함한 문자열을 반환
     * 
     * @return String : 이 객체의 정보를 포함한 문자열
     */
    @Override
    public String toString() {
        return "BoardListDto ["
                + "id=" + id
                + ", title=" + title
                + ", content=" + content
                + ", readCnt=" + readCnt
                + ", registerId=" + registerId
                + ", registerTime=" + registerTime
                + ", commentCount=" + commentCount 
                + ", member=" + member 
                + ", isNotice=" + isNotice 
                + ", category=" + category + "]";
    }
}