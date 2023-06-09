let boardWriteForm = $("#boardWriteForm");
let boardViewForm = $("#boardViewForm");
let comment = $("#commentForm");
let parentId = 0;

// 댓글 수정 폼의 update 버튼 이벤트
function fnCommentUpdate(button, categoryName) {
  	// 현재 클릭된 버튼을 기준으로 댓글 수정 폼을 찾음
  	const currentEditForm = $(button).closest('.edit-form');
  	const commentContent = currentEditForm.parent().find('#commentContent');
  	const currentCommentDiv = $(button).closest('.comment')
  	
  	// 수정된 내용을 가져와서 서버로 전송
  	const content = currentEditForm.find('textarea[name="content"]').val();
  	const commentId = currentEditForm.find('input[name="commentId"]').val();
  	const boardId = currentEditForm.find('input[name="boardId"]').val();
  	const registerId = currentEditForm.find('input[name="registerId"]').val();
  
  	$.ajax({
    	url: "/board/view/comment/update",
    	method: "POST",
    	data: {
      		commentId: commentId,
      		boardId: boardId,
      		categoryName: categoryName,
      		content: content,
      		registerId: registerId
    	},
    	success: function(data) {
	      	// 수정된 댓글 내용을 출력 영역에 적용하고 수정 폼을 숨김
	      	commentContent.text(content);
	      	currentEditForm.hide();
	      	currentCommentDiv.find('#commentContent').show();
	      	
	      	$('html').html(data);
    	},
    	error: function(xhr, status, error) {
	      	console.error(error);
	      	alert("댓글 수정 중 오류가 발생했습니다.");
    	}
  	});
}

// 댓글 삭제
function deleteComment(commentId, boardId, categoryName) {
    if (confirm("Do you want to delete it?")) {
        let form = document.createElement("form");
        form.setAttribute("charset", "UTF-8");
        form.setAttribute("method", "POST");
        form.setAttribute("action", "/board/view/comment/delete");

        let hiddenCommentId = document.createElement("input");
        hiddenCommentId.setAttribute("type", "hidden");
        hiddenCommentId.setAttribute("name", "commentId");
        hiddenCommentId.setAttribute("value", commentId);
        form.appendChild(hiddenCommentId);

        let hiddenBoardId = document.createElement("input");
        hiddenBoardId.setAttribute("type", "hidden");
        hiddenBoardId.setAttribute("name", "boardId");
        hiddenBoardId.setAttribute("value", boardId);
        form.appendChild(hiddenBoardId);
        
        let hiddenCategoryName = document.createElement("input");
		hiddenCategoryName.setAttribute("type", "hidden");
		hiddenCategoryName.setAttribute("name", "categoryName");
		hiddenCategoryName.setAttribute("value", categoryName);
		form.appendChild(hiddenCategoryName);

        document.body.appendChild(form);
        form.submit();
    }
}

// 댓글 수정폼의 cancel 버튼 이벤트
function fnCommentEdit(button) {
    // 현재 클릭된 버튼을 기준으로 댓글 div를 찾음
    const currentCommentDiv = $(button).closest('.comment');

    // 댓글 내용 출력 영역을 보이도록 변경
    currentCommentDiv.find('.reply-form-container').hide();
    currentCommentDiv.find('.edit-form-container').hide();
    currentCommentDiv.find('#commentContent').show();
    currentCommentDiv.find('.reply-list').show();
}

// 댓글 수정폼 보여주기
function editComment(commentId) {
  	const editFormContainer = document.getElementById(`edit-form-container-${commentId}`);
  	const commentContent = document.getElementById(`commentContent-${commentId}`);
  	
  	if (editFormContainer.style.display === 'none') {
        editFormContainer.style.display = 'block';
        commentContent.style.display = 'none';
    } else {
        editFormContainer.style.display = 'none';
        commentContent.style.display = 'block';
    }
  	
  	/*editFormContainer.style.display = editFormContainer.style.display === 'none' ? 'block' : 'none';*/
}

// 대댓글 수정 폼의 update 버튼 이벤트
function fnReplyUpdate(button, categoryName) {
  	// 현재 클릭된 버튼을 기준으로 댓글 수정 폼을 찾음
  	const currentEditForm = $(button).closest('.editReply-form');
  	const replyContent = currentEditForm.parent().find('#replyContent');
  	const currentCommentDiv = $(button).closest('.comment')
  	
  	// 수정된 내용을 가져와서 서버로 전송
  	const content = currentEditForm.find('textarea[name="content"]').val();
  	const replyId = currentEditForm.find('input[name="replyId"]').val();
  	const boardId = currentEditForm.find('input[name="boardId"]').val();
  	const registerId = currentEditForm.find('input[name="registerId"]').val();
  
  	$.ajax({
    	url: "/board/view/reply/update",
    	method: "POST",
    	data: {
      		replyId: replyId,
      		boardId: boardId,
      		content: content,
      		categoryName: categoryName,
      		registerId: registerId
    	},
    	success: function(data) {
	      	// 수정된 댓글 내용을 출력 영역에 적용하고 수정 폼을 숨김
	      	replyContent.text(content);
	      	currentEditForm.hide();
	      	currentCommentDiv.find('#replyContent').show();
	      	
	      	$('html').html(data);
    	},
    	error: function(xhr, status, error) {
	      	console.error(error);
	      	alert("댓글 수정 중 오류가 발생했습니다.");
    	}
  	});
}

// 대댓글 삭제
function deleteReply(replyId, boardId, categoryName) {
    if (confirm("Do you want to delete it?")) {
        let form = document.createElement("form");
        form.setAttribute("charset", "UTF-8");
        form.setAttribute("method", "POST");
        form.setAttribute("action", "/board/view/reply/delete");

        let hiddenReplyId = document.createElement("input");
        hiddenReplyId.setAttribute("type", "hidden");
        hiddenReplyId.setAttribute("name", "replyId");
        hiddenReplyId.setAttribute("value", replyId);
        form.appendChild(hiddenReplyId);

        let hiddenBoardId = document.createElement("input");
        hiddenBoardId.setAttribute("type", "hidden");
        hiddenBoardId.setAttribute("name", "boardId");
        hiddenBoardId.setAttribute("value", boardId);
        form.appendChild(hiddenBoardId);
        
        let hiddenCategoryName = document.createElement("input");
		hiddenCategoryName.setAttribute("type", "hidden");
		hiddenCategoryName.setAttribute("name", "categoryName");
		hiddenCategoryName.setAttribute("value", categoryName);
		form.appendChild(hiddenCategoryName);

        document.body.appendChild(form);
        form.submit();
    }
}

// 대댓글 수정폼의 cancel 버튼 이벤트
function fnReplyEdit(button) {
    // 현재 클릭된 버튼을 기준으로 댓글 div를 찾음
    const currentCommentDiv = $(button).closest('.comment');

    // 댓글 내용 출력 영역을 보이도록 변경
    currentCommentDiv.find('.reply-form-container').hide();
    currentCommentDiv.find('#replyContent').show();
    currentCommentDiv.find('.reply-list').show();
    currentCommentDiv.find('.editReply-form-container').hide();
}

// 대댓글 수정폼 보여주기
function editReply(replyId) {
  	const editReplyFormContainer = document.getElementById(`editReply-form-container-${replyId}`);
  	const replyContent = document.getElementById(`replyContent-${replyId}`);
  	
  	if (editReplyFormContainer.style.display === 'none') {
        editReplyFormContainer.style.display = 'block';
        replyContent.style.display = 'none';
    } else {
        editReplyFormContainer.style.display = 'none';
        replyContent.style.display = 'block';
    }
  	
  	/*editReplyFormContainer.style.display = editReplyFormContainer.style.display === 'none' ? 'block' : 'none';*/
}

// 댓글 추가
function commentAdd() {		    	
	comment.submit();
}

// 대댓글 추가
function replyAdd(event) {
	// 현재 클릭된 버튼을 기준으로 폼을 찾음
	const currentReplyForm = $(event.target).closest('.reply-form');
	currentReplyForm.submit();
}

// 댓글의 깊이 계산
function calculateDepth(comments, parentId) {
  	// 댓글의 depth 값을 저장할 배열
  	const depths = [];

  	// 댓글 리스트를 순회하며 depth 값을 계산하여 배열에 저장
  	comments.forEach(c => {
    	if (c.parentId === 0) {
      		depths[c.id] = 0;
    	} else {
      		depths[c.id] = c.depth;
    	}
  	});

  	// 대댓글 depth 
  	const result = depths[parentId] + 1;

  	// 결과 반환
  	return result;
}

// 게시글 삭제
function boardViewDelete() {
    if (confirm("Do you want to delete it?")) {
        boardViewForm.attr("action", "/board/view/delete");
		boardViewForm.attr("method","post");
		boardViewForm.submit();
    }
}

// 게시글 수정
function boardViewEdit(id) {
    if (confirm("Do you want to edit it?")) {
        $("#id").val(id);
        boardViewForm.submit();
    }
}

// 쿠키 설정
function setCookie(name, value, days) {
    var expires = "";
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "") + expires + "; path=/";
}

// 쿠키 가져오기
function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

// 게시글 추천/비추천 업데이트 및 취소
function toggleVote(boardId, type) {
    var cookieName = type + '_' + boardId;
    var currentVote = getCookie(cookieName);
    var action = "";

    if (currentVote) {
        // 쿠키가 있으면 추천/비추천을 취소
        setCookie(cookieName, '', -1); // 쿠키를 제거합니다.
        action = "/board/view/cancel" + type.charAt(0).toUpperCase() + type.slice(1);
    } else {
        // 쿠키가 없으면 추천/비추천을 등록
        setCookie(cookieName, 'true', 365); // 1년 동안 쿠키를 설정합니다.
        action = "/board/view/update" + type.charAt(0).toUpperCase() + type.slice(1);
    }

    boardViewForm.attr("action", action);
    boardViewForm.attr("method","post");
    boardViewForm.submit();
}