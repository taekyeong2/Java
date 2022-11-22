package com.yedam.project.board.notice;

import java.util.List;
import java.util.Scanner;

import com.yedam.project.board.common.LoginControl;
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
			boardType();
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
				int selectNum = boardNumInput();
				crun = selectCheck(selectNum);
				while (crun) {
					// 조회한 게시글 출력
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
						// 삭제시 뒤로가기
						crun = boardCheck(selectNum);
						break;
					case 3:
						// 댓글작성
						notiCWrite(selectNum);
						break;
					case 4:

						// 댓글수정
						notiCUpdate(selectNum);
						break;
					case 5:
						// 댓글삭제
						notiCDelete(selectNum);
						break;
					case 0:
						// 뒤로가기
						crun = false;
						break;
					default:
						// 잘못된 번호 입력시 에러메세지
						error();
					}
				}
				break;
			case 0:
				// 뒤로가기
				run = false;
				break;
			default:
				// 잘못된 번호 입력시 에러메세지
				error();
			}

		}
	}

	// 게시판이름 출력
	private void boardType() {
		System.out.println("< 공지게시판 >\n");
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
		System.out.println("=========================");
		System.out.println(" 1.작성 | 2.조회 | 0.뒤로가기 ");
		System.out.println("=========================");
	}

	// 메뉴선택
	private int menuSelect() {
		System.out.println("메뉴를 선택해 주세요");
		int menNo = Integer.parseInt(sc.nextLine());
		return menNo;
	}

	// 게시글작성
	private void notiWrite() {
		// 관리자 권한 확인
		boolean checkRole = checkRole();
		if (checkRole == true) {
			NoticeVO notiVO = writeInfo();
			notiDAO.insert(notiVO);
		} else {
			System.out.println("권한이 없습니다.\n");
			return;
		}

	}

	// 관리자 권한 확인
	private boolean checkRole() {
		boolean checkRole = false;
		// 현재 로그인정보의 역할값 가져옴
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

	// 조회할 게시글 번호
	public int boardNumInput() {
		System.out.println("조회할 게시글 번호를 입력해 주세요.");
		System.out.println("게시글 번호 > ");
		int selectNum = Integer.parseInt(sc.nextLine());
		return selectNum;
	}

	// 게시글 단건 유무
	private boolean selectCheck(int notiNum) {
		boolean check = true;
		NoticeVO notiVO = notiDAO.selectOne(notiNum);
		if (notiVO == null) {
			System.out.println("없는 게시글 입니다.\n");
			check = false;
		}
		return check;
	}

	// 게시글 단건조회 + 게시글안의 댓글 조회
	private void notiSelect(int notiNum) {
		NoticeVO notiVO = notiDAO.selectOne(notiNum);
		System.out.println(notiVO);
		// 댓글조회
		noticeCoSelect(notiNum);
	}

	// 조회할 게시물에 있는 댓글 전체조회
	private void noticeCoSelect(int notiNum) {
		List<NoticeCommentVO> list = notiCDAO.selectAll(notiNum);
		String notiCStr = "";
		for (NoticeCommentVO comment : list) {
			notiCStr += comment;
		}
		System.out.println("\n" + "댓글 > ");
		System.out.println(notiCStr);
	}

	// 메뉴출력
	private void menuPrint() {
		System.out.println("========================================================");
		System.out.println("1.수정 | 2.삭제 | 3.댓글작성 | 4.댓글수정 | 5.댓글삭제 | 0.뒤로가기");
		System.out.println("========================================================");
	}

	// 게시글 수정
	private void notiUpdate(int notiNum) {
		String confirm;
		// 역할값확인
		if (checkRole() == true) {
			System.out.println("게시글 내용을 수정하시겠습니까? (y/n)");
			confirm = sc.nextLine();
			if (confirm.toLowerCase().equals("y")) {
				String content = updateContent();
				notiDAO.update(notiNum, content);
			} else {
				return;
			}
		} else {
			System.out.println("권한이 없습니다.\n");
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
		// 역할값확인
		if (checkRole() == true) {
			System.out.println("게시글 내용을 삭제하시겠습니까? (y/n)");
			confirm = sc.nextLine();
			if (confirm.toLowerCase().equals("y")) {
				notiDAO.delete(notiNum);
				notiCDAO.deleteAll(notiNum);
			} else {
				return;
			}
		} else {
			System.out.println("권한이 없습니다.\n");
			return;
		}
	}

	// 게시글 유무 확인
	private boolean boardCheck(int notiNum) {
		boolean check = true;
		NoticeVO notiVO = notiDAO.selectOne(notiNum);
		if (notiVO == null) {
			check = false;
		}
		return check;
	}

	// 댓글작성
	private void notiCWrite(int notiNum) {
		NoticeCommentVO notiCVO = writeCInfo(notiNum);
		notiCDAO.insert(notiCVO);
	}

	// 댓글작성내용
	private NoticeCommentVO writeCInfo(int notiNum) {
		List<NoticeCommentVO> checkList = notiCDAO.selectAll(notiNum);
		NoticeCommentVO notiCVO = new NoticeCommentVO();
		notiCVO.setMemId(memId);
		System.out.println("댓글 > ");
		notiCVO.setNoticeCContent(sc.nextLine());
		notiCVO.setNoticeNum(notiNum);
		// 댓글리스트 사이즈를 통해 번호누적
		int num = checkList.size();
		++num;
		notiCVO.setNoticeCNum(num);

		return notiCVO;
	}

	// 댓글수정
	private void notiCUpdate(int notiNum) {
		System.out.println("수정할 댓글번호를 입력해주세요");
		int notiCNum = notiCoInput();
		int checkCNum = commentCheck(notiNum, notiCNum);
			List<NoticeCommentVO> notiCVO = notiCDAO.selectAll(notiNum);
			for (NoticeCommentVO notiCheck : notiCVO) {
				// 입력한 댓글번호를 가진 댓글이 있는지 체크
				if (checkCNum > 0) {
					if (notiCheck.getNoticeCNum() == notiCNum) {
						// 본인 아이디가 맞는지 확인
						if (notiCheck.getMemId().equals(memId)) {
							String content = updateContent();
							notiCDAO.update(notiCNum, content, notiNum);
						} else {
							System.out.println("본인이 아닙니다.\n");
							return;
						}

					}
				} else {
					System.out.println("댓글이 존재하지 않습니다.\n");
					return;
				}
			}
		}
	

	// 댓글 유무확인
	private int commentCheck(int notiNum, int notiCNum) {
		int checkCNum = 0;
		List<NoticeCommentVO> notiCVO = notiCDAO.selectAll(notiNum);
		for (NoticeCommentVO notiCheck : notiCVO) {
			// 댓글번호 더한값으로 존재유무확인
			if (notiCheck.getNoticeCNum() == notiCNum) {
				checkCNum += notiCheck.getNoticeCNum();
			}
		}
		return checkCNum;
	}

	// 수정할 댓글 번호
	private int notiCoInput() {
		System.out.println("댓글번호 > ");
		int notiCNum = Integer.parseInt(sc.nextLine());
		return notiCNum;
	}

	// 댓글삭제
	private void notiCDelete(int notiNum) {
		System.out.println("삭제할 댓글번호를 입력해주세요");
		int notiCNum = notiCoInput();
		int checkCNum = commentCheck(notiNum, notiCNum);
		List<NoticeCommentVO> notiCVO = notiCDAO.selectAll(notiNum);
		for (NoticeCommentVO notiCheck : notiCVO) {
			// 댓글존재유무 체크
			if (checkCNum > 0) {
				if (notiCheck.getNoticeCNum() == notiCNum) {
					// 본인확인
					if (notiCheck.getMemId().equals(memId)) {
						notiCDAO.delete(notiCNum, notiNum);
					} else {
						System.out.println("본인이 아닙니다.\n");
						return;
					}
				}

			} else {
				System.out.println("댓글이 존재하지 않습니다.\n");
				return;
			}
		}
	}

	// 메뉴잘못선택시 출력
	private void error() {
		System.out.println("올바른 메뉴를 입려해 주세요\n");
	}

}
