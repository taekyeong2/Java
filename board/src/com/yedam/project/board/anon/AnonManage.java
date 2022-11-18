package com.yedam.project.board.anon;

import java.util.Scanner;

import com.yedam.project.board.anon.comment.AnonCommentDAO;
import com.yedam.project.board.anon.comment.AnonCommentDAOImpl;

public class AnonManage {
	Scanner sc = new Scanner(System.in);
	AnonDAO anonDAO = AnonDAOImpl.getInstance();
	AnonCommentDAO anonCDAO = AnonCommentDAOImpl.getInstance();
	
	public AnonManage() {
		while(true) {
			//익명게시판 접속
			anonDAO.selectAll();
			
			//메뉴출력
			momMenuPrint();
			
			//메뉴선택
			int menNo = menuSelect()
			
			
			
			
		}
	}

}
