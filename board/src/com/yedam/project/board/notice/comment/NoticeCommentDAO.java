package com.yedam.project.board.notice.comment;

import java.util.List;

public interface NoticeCommentDAO {
	
	//댓글 전체 출력
	List<NoticeCommentVO> selectAll(int noticeNum);
	
	//댓글 단건 출력
	public  NoticeCommentVO selectOne(int noticeCNum);
	
	//댓글 등록
	void insert (NoticeCommentVO noticeCVO);
		
	//댓글 수정
	void update (int noticeCNum, String noticeCContent);
		
	//게시글 삭제
	void delete (int noticeCNum);
	
	//댓글 전체 삭제
	void deleteAll(int noticeNum);
}
