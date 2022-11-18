package com.yedam.project.board.member;

public interface MemberDAO {
	
	//회원정보 단건조회(로그인 중복값 확인할때)
	MemberVO selectOne (MemberVO memberVO);
	
	//회원가입(회원정보 등록)
	void insert (MemberVO memberVO);
}
