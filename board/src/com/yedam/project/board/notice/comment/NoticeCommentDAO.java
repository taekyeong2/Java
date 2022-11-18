package com.yedam.project.board.notice.comment;

import java.util.List;

public interface NoticeCommentDAO {
	
	//댓글 전체 출력
	List<NoticeCommentVO> selectAll(NoticeCommentVO noticeCVO);
		
	//댓글 등록
	void insert (NoticeCommentVO noticeCVO);
		
	//댓글 수정
	void update (NoticeCommentVO noticeCVO);
		
	//게시글 삭제
	void delete (NoticeCommentVO noticeCVO);
}
