package com.yedam.project.board.free.comment;

import java.util.List;

public interface FreeCommentDAO {
	
	//댓글 전체 출력
	List<FreeCommentVO> selectAll(FreeCommentVO freeCVO);
		
	//댓글 등록
	void insert (FreeCommentVO freeCVO);
		
	//댓글 수정
	void update (FreeCommentVO freeCVO);
		
	//게시글 삭제
	void delete (FreeCommentVO freeCVO);
}
