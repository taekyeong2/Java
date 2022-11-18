package com.yedam.project.board.anon.comment;

import java.util.List;

public interface AnonCommentDAO {
	
	//댓글 전체 출력
	List<AnonCommentVO> selectAll(AnonCommentVO anonCVO);
	
	//댓글 등록
	void insert (AnonCommentVO anonCVO);
	
	//댓글 수정
	void update (AnonCommentVO anonCVO);
	
	//게시글 삭제
	void delete (AnonCommentVO anonCVO);
	
}
