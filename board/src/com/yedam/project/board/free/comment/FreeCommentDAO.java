package com.yedam.project.board.free.comment;

import java.util.List;

public interface FreeCommentDAO {
	
	//댓글 전체 출력
	List<FreeCommentVO> selectAll(int freeNum);
		
	//댓글 단건 출력
	public FreeCommentVO selectOne(int freeCNum);
	//댓글 등록
	void insert (FreeCommentVO freeCVO);
		
	//댓글 수정
	void update (int freeCNum, String freeCContent);
		
	//게시글 삭제
	void delete (int freeCNum);
	
	//댓글 전체 삭제
	void deleteAll(int freeNum);
}
