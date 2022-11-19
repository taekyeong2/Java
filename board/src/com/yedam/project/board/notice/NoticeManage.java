package com.yedam.project.board.notice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.yedam.project.board.common.LoginControl;
import com.yedam.project.board.free.comment.FreeCommentVO;
import com.yedam.project.board.notice.comment.NoticeCommentDAO;
import com.yedam.project.board.notice.comment.NoticeCommentDAOImpl;
import com.yedam.project.board.notice.comment.NoticeCommentVO;

public class NoticeManage {
	Scanner sc = new Scanner(System.in);
	NoticeDAO notiDAO = NoticeDAOImpl.getInstance();
	NoticeCommentDAO notiCDAO = NoticeCommentDAOImpl.getInstance();
	String memId = LoginControl.getLoginInfo().getMemId();

	public NoticeManage() {
		boolean run = true;
		while (run) {
			// 공지게시판 접속
			notiSelectAll();
			// 메뉴출력
			momMenuPrint();
			// 메뉴선택
			int menNo = menuSelect();
			switch (menNo) {
			case 1:
				// 작성
				notiWrite();
				break;
			case 2:
				// 조회
				boolean crun;
				System.out.println("조회할 게시글 번호를 입력해 주세요.");
				System.out.println("게시글 번호 > ");
				int selectNum = Integer.parseInt(sc.nextLine());
				crun = selectCheck(selectNum);
				while (crun) {
					notiSelect(selectNum);
					// 메뉴출력
					menuPrint();
					menNo = menuSelect();
					// 조회메뉴선택
					switch (menNo) {
					case 1:
						// 수정
						notiUpdate(selectNum);
						break;
					case 2:
						// 삭제
						notiDelete(selectNum);
						crun = false;
						break;
					case 3:
						// 댓글작성
						notiCWrite(selectNum);
						break;
					case 4:
						// 댓글수정
						notiCUpdate();
						break;
					case 5:
						// 댓글삭제
						notiCDelete();
						break;
					case 0:
						// 뒤로가기
						crun = false;
						break;
					default:
						error();
					}
				}
				break;
			case 0:
				// 뒤로가기
				run = false;
				break;
			default:
				error();
			}

		}
	}

	// 전체 게시글 불러오기
	private void notiSelectAll() {
		List<NoticeVO> notiVOList = notiDAO.selectAll();
		String str = "";
		for (NoticeVO list : notiVOList) {
			str += list;
		}
		System.out.println(str);
	}

	// 엄마메뉴 출력
	private void momMenuPrint() {
		System.out.println("===========================");
		System.out.println("1.작성 |2.조회 |0.뒤로가기");
		System.out.println("===========================");
	}

	// 메뉴선택
	private int menuSelect() {
		System.out.println("메뉴를 선택해 주세요");
		int menNo = Integer.parseInt(sc.nextLine());
		return menNo;
	}

	// 게시글작성
	private void notiWrite() {
		boolean checkRole = checkRole();
		if (checkRole == true) {
			NoticeVO notiVO = writeInfo();
			notiDAO.insert(notiVO);
		} else {
			System.out.println("권한이 없습니다.");
			return;
		}

	}

	// 관리자 권한 확인
	private boolean checkRole() {
		boolean checkRole = false;
		int role = LoginControl.getLoginInfo().getMemRole();
		if (role == 0) {
			checkRole = true;
		}
		return checkRole;
	}

	// 게시글작성 내용입력
	private NoticeVO writeInfo() {
		NoticeVO notiVO = new NoticeVO();
		notiVO.setMemId("관리자");
		System.out.println("제목 > ");
		notiVO.setNoticeTitle(sc.nextLine());
		System.out.println("내용 > ");
		notiVO.setNoticeContent(sc.nextLine());

		return notiVO;
	}

	// 게시글 단건 유무
	private boolean selectCheck(int notiNum) {
		boolean check = true;
		NoticeVO notiVO = notiDAO.selectOne(notiNum);
		if (notiVO == null) {
			System.out.println("없는 게시글 입니다.");
			check = false;
		}
		return check;
	}

	// 게시글 단건조회
	private void notiSelect(int notiNum) {
		NoticeVO notiVO = notiDAO.selectOne(notiNum);
		List<NoticeCommentVO> list = notiCDAO.selectAll(notiNum);
		String notiStr = "";
		String notiCStr = "";
		notiStr += notiVO;
		if (notiVO == null) {
			System.out.println("없는 게시글 입니다.");
		} else {
			notiStr += notiVO;
			for (NoticeCommentVO comment : list) {
				notiCStr += comment;
			}
			System.out.println(notiStr);
			System.out.println("\n" + "댓글 > ");
			System.out.println(notiCStr);
		}
	}

	// 메뉴출력
	private void menuPrint() {
		System.out.println("==============================================");
		System.out.println("1.수정 |2.삭제 |3.댓글작성 |4.댓글수정 |5.댓글삭제 |0.뒤로가기");
		System.out.println("==============================================");
	}

	// 게시글 수정
	private void notiUpdate(int notiNum) {
		String confirm;
		System.out.println("게시글 내용을 수정하시겠습니까? (y/n)");
		confirm = sc.nextLine();
		boolean checkRole = checkRole();
		if(checkRole == true) {
			if (confirm.toLowerCase().equals("y")) {
				String content = updateContent();
				notiDAO.update(notiNum, content);
			}else {
				return;
			}
		}else {
			System.out.println("권한이 없습니다.");
			return;
		}
		
	}

	// 수정할 내용
	private String updateContent() {
		String content = null;
		System.out.println("내용 > ");
		content = sc.nextLine();
		return content;
	}

	// 게시글삭제 + 그게시글의 전체 댓글삭제
	private void notiDelete(int notiNum) {
		String confirm;
		System.out.println("게시글 내용을 삭제하시겠습니까? (y/n)");
		confirm = sc.nextLine();

		if (confirm.toLowerCase().equals("y")) {
			boolean checkRole = checkRole();
			if (checkRole = true) {
				notiDAO.delete(notiNum);
				notiCDAO.deleteAll(notiNum);
			} else {
				System.out.println("작성하신 글이 아닙니다.");
				return;
			}
		} else {
			return;
		}
	}

	// 댓글작성
	private void notiCWrite(int notiNum) {
		NoticeCommentVO notiCVO = writeCInfo(notiNum);
		notiCDAO.insert(notiCVO);
	}

	// 댓글작성내용
	private NoticeCommentVO writeCInfo(int notiNum) {
		NoticeCommentVO notiCVO = new NoticeCommentVO();
		notiCVO.setMemId(memId);
		System.out.println("댓글 > ");
		notiCVO.setNoticeCContent(sc.nextLine());
		notiCVO.setNoticeNum(notiNum);

		return notiCVO;
	}

	// 댓글수정
	private void notiCUpdate() {
		System.out.println("수정할 댓글번호를 입력해주세요");
		int notiCNum = notiCoInput();
		boolean notiCheck = checkC(notiCNum);
		if (notiCheck = true) {
			String content = updateContent();
			notiCDAO.update(notiCNum, content);
		} else {
			System.out.println("본인이 아닙니다.");
			return;
		}
	}

	// 댓글삭제 시 아이디 비교
	private boolean checkC(int notiCNum) {
		boolean checkC = false;
		List<NoticeCommentVO> notiCVO = new ArrayList<>();
		notiCVO = notiCDAO.selectAll(notiCNum);
		for (NoticeCommentVO find : notiCVO) {
			if (find.getMemId().equals(memId)) {
				checkC = true;
			}
		}
		return checkC;
	}

	// 수정할 댓글 번호
	private int notiCoInput() {
		System.out.println("댓글번호 > ");
		int notiCNum = Integer.parseInt(sc.nextLine());
		return notiCNum;
	}

	// 댓글삭제
	private void notiCDelete() {
		System.out.println("삭제할 댓글번호를 입력해주세요");
		int notiCNum = notiCoInput();
		boolean notiCheck = checkC(notiCNum);
		if (notiCheck = true) {
			notiCDAO.delete(notiCNum);
		} else {
			System.out.println("본인이 아닙니다.");
			return;
		}
	}

	// 메뉴잘못선택시 출력
	private void error() {
		System.out.println("올바른 메뉴를 입려해 주세요");
	}

}
