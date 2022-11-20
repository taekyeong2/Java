package com.yedam.project.board.common;

import java.util.Scanner;

import com.yedam.project.board.anon.AnonManage;
import com.yedam.project.board.member.MemberDAO;
import com.yedam.project.board.member.MemberDAOImpl;
import com.yedam.project.board.member.MemberVO;

public class Management {
	Scanner sc = new Scanner(System.in);
	MemberDAO memDAO = MemberDAOImpl.getInstance();

	public Management() {
		boolean run = true;
		while (run) {
			// 메뉴출력
			momMenuPrint();
			// 메뉴선택
			int menNo = menuSelect();
			switch (menNo) {
			case 1:
				// 로그인
				new LoginControl();
				break;

			case 2:
				// 회원가입
				memberJoin();
				break;

			case 3:
				// 익명게시판
				new AnonManage();
				break;

			case 0:
				run = false;
				break;

			default:
				error();

			}

		}
	}

	// 메뉴 출력
	private void momMenuPrint() {
		System.out.println("================================");
		System.out.println("1.로그인 |2.회원가입 |3.익명게시판 |0.종료");
		System.out.println("================================");
	}

	// 메뉴선택
	private int menuSelect() {
		System.out.println("메뉴를 선택해 주세요");
		int menNo = Integer.parseInt(sc.nextLine());
		return menNo;
	}
	
	//회원가입
	private void memberJoin() {
		MemberVO memVO = inputMemInfo();
		memDAO.insert(memVO);
	}
	//회원정보입력
	private MemberVO inputMemInfo() {
		MemberVO memberInfo = new MemberVO();
		System.out.println("아이디 > ");
		memberInfo.setMemId(sc.nextLine());
		System.out.println("패스워드 > ");
		memberInfo.setMemPw(sc.nextLine());
		System.out.println("이름 > ");
		memberInfo.setMemName(sc.nextLine());
		System.out.println("이메일(-----@-----.com) > ");
		memberInfo.setMemEmail(sc.nextLine());
		
		return memberInfo;
	}
	
	//메뉴잘못선택시 출력
	private void error() {
			System.out.println("올바른 메뉴를 입려해 주세요");			
	}


}