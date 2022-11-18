package com.yedam.project.board.free;

import java.util.List;

public interface FreeDAO {
	
	//게시글 전체출력
	List<FreeVO> selectAll();
	
	//게시글 단건조회
	FreeVO selectOne(FreeVO freeVO);
		
	//게시글 등록
	void insert(FreeVO freeVO);
		
    //게시글 수정
	void update (FreeVO freeVO);
		
	//게시글 삭세
	void delete(FreeVO freeVO);
	
}
