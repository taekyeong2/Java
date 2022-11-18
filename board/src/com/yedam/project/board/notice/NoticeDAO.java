package com.yedam.project.board.notice;

import java.util.List;

public interface NoticeDAO {
	//게시글 전체출력
	List<NoticeVO> selectAll();
	
	//게시글 단건조회
	NoticeVO selectOne(NoticeVO noticeVO);
		
	//게시글 등록
	void insert(NoticeVO noticeVO);
		
    //게시글 수정
	void update (NoticeVO noticeVO);
		
	//게시글 삭세
	void delete(NoticeVO noticeVO);
}
