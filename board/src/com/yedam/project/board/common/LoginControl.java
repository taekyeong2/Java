package com.yedam.project.board.common;

import java.util.Scanner;

import com.yedam.project.board.free.FreeManage;
import com.yedam.project.board.member.MemberDAO;
import com.yedam.project.board.member.MemberDAOImpl;
import com.yedam.project.board.member.MemberVO;
import com.yedam.project.board.notice.NoticeManage;

public class LoginControl {

	Scanner sc = new Scanner(System.in);
	MemberDAO memberDAO = MemberDAOImpl.getInstance();
	
	//메소드 호출시 로그인 성공시 로그인한 정보 저장역할
	private static MemberVO loginInfo = null;
	public static MemberVO getLoginInfo() {
		return loginInfo;
	}
	
	
	public LoginControl() {
		boolean run = login();
		while (run) {
			// 게시판 메뉴 출력
			menuPrint();
			// 메뉴선택
			int menNo = menuSelect();
			switch (menNo) {
			case 1:
				// 공지게시판
				new NoticeManage();
				break;
			case 2:
				// 자유게시판
				new FreeManage();
				break;
			case 0:
				// 로그아웃
				run = false;
				break;
			default:
				error();
			}
		}
	}
	
	    // 메뉴출력
		private void menuPrint() {
			System.out.println("================================");
			System.out.println("1.공지게시판 | 2.자유게시판 | 0.로그아웃 ");
			System.out.println("================================");
		}
	
		// 메뉴선택
		private int menuSelect() {
			System.out.println("메뉴를 선택해 주세요");
			int menNo = Integer.parseInt(sc.nextLine());
			return menNo;
		}

	//로그인 - 참 거짓으로 리턴
	private boolean login() {
		boolean login = true;
		//로그인 정보 입력
		MemberVO inputInfo = inputMemberInfo();
		//로그인 정보로 조회한 값을 넣기
		loginInfo = memberDAO.selectOne(inputInfo);
		
		//로그인성공
		//로그인 정보가 없으면
		if(loginInfo == null) {
			System.out.println("아이디가 존재하지 않습니다.\n");
			login = false;
		}else {//로그인 정보가 있다면
			//입력된 로그인 정보 비번과 입력한 비번이 같으면
			if(loginInfo.getMemPw().equals(inputInfo.getMemPw())) {
				System.out.println(loginInfo.getMemId()+"님 반갑습니다.\n");
			}else {
				System.out.println("비밀번호가 일치하지 않습니다.\n");
				login = false;
			}
		}
		return login;
		
		  
	}
	
	//로그인정보 입력
	private MemberVO inputMemberInfo() {
		MemberVO memberInfo = new MemberVO();
		System.out.println("아이디 > "); 
		memberInfo.setMemId(sc.nextLine());
		System.out.println("비밀번호 > "); 
		memberInfo.setMemPw(sc.nextLine());
		
		return memberInfo;
	}
	
	//메뉴잘못선택시 출력
	private void error() {
			System.out.println("올바른 메뉴를 입력해 주세요.\n");
	}

	
	
}
