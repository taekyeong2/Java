package com.yedam.project.board.free;

import java.util.List;

public interface FreeDAO {
	
	//게시글 전체출력
	List<FreeVO> selectAll();
	
	//게시글 검색
	List<FreeVO> selectAll(String memId);
	
	//게시글 단건조회
	FreeVO selectOne(int freeNum);
		
	//게시글 등록
	void insert(FreeVO freeVO);
		
    //게시글 수정
	void update (int freeNum, String freeContent);
		
	//게시글 삭세
	void delete(int freeNum);
	
}
