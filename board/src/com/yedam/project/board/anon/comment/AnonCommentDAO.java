package com.yedam.project.board.anon.comment;

import java.util.List;

import com.yedam.project.board.anon.AnonVO;

public interface AnonCommentDAO {
	
	//댓글 전체 출력
	List<AnonCommentVO> selectAll(int anonNum);
	
	//댓글 단건 조회
	AnonCommentVO selectOne(int anonCNum);
	
	//댓글 등록
	void insert (AnonCommentVO anonCVO);
	
	//댓글 수정
	void update (String anonCContent, int anonCNum);
	
	//댓글 삭제
	void delete (int anonCNum);
	
	//댓글 전체 삭제
	void deleteAll(int anonNum);
	
}
