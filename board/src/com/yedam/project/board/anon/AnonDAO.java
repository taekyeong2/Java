package com.yedam.project.board.anon;

import java.util.List;

public interface AnonDAO {
	
	//게시글 전체조회
	List<AnonVO> selectAll(AnonVO anonVO);
	
	//게시글 단건조회
	AnonVO selectOne(AnonVO anonVO);
	
	//게시글 등록
	void insert(AnonVO anonVO);
	
	//게시글 수정
	void update (AnonVO anonVO);
	
	//게시글 삭세
	void delete(AnonVO anonVO);
}
