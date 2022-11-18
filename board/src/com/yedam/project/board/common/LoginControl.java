package com.yedam.project.board.common;

import java.util.Scanner;

import com.yedam.project.board.member.MemberDAO;
import com.yedam.project.board.member.MemberDAOImpl;
import com.yedam.project.board.member.MemberVO;

public class LoginControl {
    //로그인. 회원가입 창 + 로그인 성공시 로그인한 정보 저장역할
	Scanner sc = new Scanner(System.in);
	//어디서든 쓸수있게
	private static MemberVO loginInfo = null;
	public static MemberVO getLoginInfo() {
		return loginInfo;
	}
	
	MemberDAO memberDAO = MemberDAOImpl.getInstance();
	
	//회원가입
	private void newLogin() {
		MemberVO memberVO = newMemberInfo();
		memberDAO.insert(memberVO);		
	}
	
	//회원정보입력
	private MemberVO newMemberInfo() {
		MemberVO memberInfo = new MemberVO();
		System.out.println("아이디 > ");
		memberInfo.setMemId(sc.nextLine());
		System.out.println("패스워드 > ");
		memberInfo.setMemId(sc.nextLine());
		System.out.println("이름 > ");
		memberInfo.setMemId(sc.nextLine());
		System.out.println("이메일(-----@-----.com) > ");
		memberInfo.setMemId(sc.nextLine());
		
		return memberInfo;
	}
	
	//로그인   => manage에 관련
	private void login() {
		MemberVO inputInfo = inputMemberInfo();
		loginInfo = memberDAO.selectOne(inputInfo);
		
		//로그인실패
		if(loginInfo == null) return;
		
		//로그인 성공 -> 메뉴실행
		new Manege().run();
		
		  
	}
	
	//로그인정보 입력
	private MemberVO inputMemberInfo() {
		MemberVO memberInfo = new MemberVO();
		System.out.println("아이디 > "); 
		memberInfo.setMemId(sc.nextLine());
		System.out.println("패스워드 > "); 
		memberInfo.setMemId(sc.nextLine());
		
		return memberInfo;
	}
	
	
}
