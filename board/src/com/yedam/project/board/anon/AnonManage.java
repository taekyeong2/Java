package com.yedam.project.board.anon;

import java.util.List;
import java.util.Scanner;

import com.yedam.project.board.anon.comment.AnonCommentDAO;
import com.yedam.project.board.anon.comment.AnonCommentDAOImpl;
import com.yedam.project.board.anon.comment.AnonCommentVO;
import com.yedam.project.board.notice.NoticeVO;

public class AnonManage {
	Scanner sc = new Scanner(System.in);
	AnonDAO anonDAO = AnonDAOImpl.getInstance();
	AnonCommentDAO anonCDAO = AnonCommentDAOImpl.getInstance();

	public AnonManage() {
		boolean run = true;
		while (run) {
			// 익명게시판 접속
			boardType();
			anonSelectAll();
			// 메뉴출력
			momMenuPrint();
			// 메뉴선택
			int menNo = menuSelect();

			switch (menNo) {
			case 1:
				// 작성
				anonWrite();
				break;
			case 2:
				// 조회
				boolean crun;
				System.out.println("조회할 게시글 번호를 입력해 주세요.");
				System.out.println("게시글 번호 > ");
				int selectNum = Integer.parseInt(sc.nextLine());
				crun = selectCheck(selectNum);
				while (crun) {
					anonSelect(selectNum);
					// 메뉴출력
					menuPrint();
					menNo = menuSelect();
					// 조회메뉴 선택
					switch (menNo) {
					case 1:
						// 수정
						anonUpdate(selectNum);
						break;
					case 2:
						// 삭제
						anonDelete(selectNum);
						crun = boardCheck(selectNum);
						break;
					case 3:
						// 댓글작성
						anonCWrite(selectNum);
						break;
					case 4:
						// 댓글수정
						anonCUpdate();
						break;
					case 5:
						// 댓글삭제
						anonCDelete();
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
			case 3:
				// 검색
				anonSearch();
				break;
			case 0:
				run = false;
				// 뒤로가기
				break;
			default:
				error();

			}

		}
	}
	
	//게시판이름 출력
	private void boardType() {
		System.out.println("< 익명게시판 >");
		System.out.println();
	}

	// 전체 게시글 불러오기
	private void anonSelectAll() {
		List<AnonVO> anonVOList = anonDAO.selectAll();
		String str = "";
		for (AnonVO list : anonVOList) {
			str += list;
		}
		System.out.println(str);
	}

	// 엄마메뉴 출력
	private void momMenuPrint() {
		System.out.println("================================");
		System.out.println(" 1.작성 | 2.조회 | 3.검색 | 0.뒤로가기 ");
		System.out.println("================================");
	}

	// 메뉴선택
	private int menuSelect() {
		System.out.println("메뉴를 선택해 주세요");
		int menNo = Integer.parseInt(sc.nextLine());
		return menNo;
	}

	// 게시글작성
	private void anonWrite() {
		AnonVO anonVO = writeInfo();
		anonDAO.insert(anonVO);

	}

	// 게시글작성 내용입력
	private AnonVO writeInfo() {
		AnonVO anonVO = new AnonVO();
		System.out.println("닉네임 > ");
		anonVO.setAnonName(sc.nextLine());
		System.out.println("패스워드 > ");
		anonVO.setAnonPw(sc.nextLine());
		System.out.println("제목 > ");
		anonVO.setAnonTitle(sc.nextLine());
		System.out.println("내용 > ");
		anonVO.setAnonContent(sc.nextLine());

		return anonVO;
	}

	// 게시글 단건 유무
	private boolean selectCheck(int anonNum) {
		boolean check = true;
		AnonVO anonVO = anonDAO.selectOne(anonNum);
		if (anonVO == null) {
			System.out.println("없는 게시글 입니다.");
			System.out.println();
			check = false;
		}
		return check;
	}

	// 게시글 단건조회
	private void anonSelect(int anonNum) {
		AnonVO anonVO = anonDAO.selectOne(anonNum);
		List<AnonCommentVO> list = anonCDAO.selectAll(anonNum);
		String anonStr = "";
		String anonCStr = "";
		if (anonVO == null) {
			System.out.println("없는 게시글 입니다.");
			System.out.println();
		} else {
			anonStr += anonVO;
			for (AnonCommentVO comment : list) {
				anonCStr += comment;
			}
			System.out.println(anonStr);
			System.out.println("\n" + "댓글 > ");
			System.out.println(anonCStr);
		}
	}

	// 메뉴출력
	private void menuPrint() {
		System.out.println("=================================");
		System.out.println("1.수정 | 2.삭제 ");
		System.out.println("3.댓글작성 |4.댓글수정 |5.댓글삭제 |0.뒤로가기");
		System.out.println("=================================");
	}

	// 게시글수정
	private void anonUpdate(int anonNum) {
		String confirm;
		if (check(anonNum) == true) {
			System.out.println("게시글 내용을 수정하시겠습니까? (y/n)");
			confirm = sc.nextLine();
			if (confirm.toLowerCase().equals("y")) {
				String content = updateContent();
				anonDAO.update(anonNum, content);
			} else {
				return;
			}
		} else {
			System.out.println("패스워드가 일치하지 않습니다.");
			System.out.println();
			return;
		}
	}

	// 본인확인(비번으로)
	private boolean check(int anonNum) {
		boolean check = false;
		System.out.println("패스워드를 입력해주세요");
		String checkPw = sc.nextLine();
		AnonVO anonVO = anonDAO.selectCheck(anonNum);
		if (checkPw.equals(anonVO.getAnonPw())) {
			check = true;
		}
		return check;

	}

	// 수정할 내용
	private String updateContent() {
		String content = null;
		System.out.println("내용 > ");
		content = sc.nextLine();
		return content;
	}

	// 게시글삭제+그 게시글 댓글전체 삭제
	private void anonDelete(int anonNum) {
		String confirm;
		if (check(anonNum) == true) {
			System.out.println("게시글 내용을 삭제하시겠습니까? (y/n)");
			confirm = sc.nextLine();
			if (confirm.toLowerCase().equals("y")) {
				anonDAO.delete(anonNum);
				anonCDAO.deleteAll(anonNum);
			} else {
				return;
			}
		} else {
			System.out.println("패스워드가 일치하지 않습니다.");
			System.out.println();
			return;
		}
	}
	
	//게시글 유무 확인
	private boolean boardCheck(int anonNum) {
		boolean check = true;
		AnonVO anonVO = anonDAO.selectOne(anonNum);
		if(anonVO == null) {
			check = false;
		}
		return check;
	}

	// 댓글작성
	private void anonCWrite(int anonNum) {
		AnonCommentVO anonCVO = writeCInfo(anonNum);
		anonCDAO.insert(anonCVO);
	}

	// 댓글작성내용
	private AnonCommentVO writeCInfo(int anonNum) {
		AnonCommentVO anonCVO = new AnonCommentVO();
		System.out.println("닉네임 > ");
		anonCVO.setAnonCName(sc.nextLine());
		System.out.println("패스워드 > ");
		anonCVO.setAnonCPw(sc.nextLine());
		System.out.println("댓글 > ");
		anonCVO.setAnonCContent(sc.nextLine());
		anonCVO.setAnonNum(anonNum);

		return anonCVO;
	}

	// 댓글수정
	private void anonCUpdate() {
		System.out.println("수정할 댓글번호를 입력해주세요");
		int anonCNum = anonCoInput();
		if (checkC(anonCNum) == true) {
			String content = updateContent();
			anonCDAO.update(anonCNum, content);
		} else {
			System.out.println("패스워드가 일치하지 않습니다.");
			System.out.println();
			return;
		}
	}

	// 수정학 댓글 번호
	private int anonCoInput() {
		System.out.println("댓글번호 > ");
		int anonCNum = Integer.parseInt(sc.nextLine());
		return anonCNum;
	}

	// 댓글 비밀번호 확인
	private boolean checkC(int anonCNum) {
		boolean checkC = false;
		System.out.println("패스워드를 입력해주세요");
		String checkPw = sc.nextLine();
		AnonCommentVO anonCVO = anonCDAO.selectOne(anonCNum);
		if (checkPw.equals(anonCVO.getAnonCPw())) {
			checkC = true;
		}
		return checkC;

	}

	// 댓글삭제
	private void anonCDelete() {
		System.out.println("삭제할 댓글번호를 입력해주세요");
		int anonCNum = anonCoInput();
		if (checkC(anonCNum) == true) {
			anonCDAO.delete(anonCNum);
		} else {
			System.out.println("패스워드가 일치하지 않습니다.");
			System.out.println();
			return;
		}
	}

	// 글검색 - 닉네임으로
	private void anonSearch() {
		String find = inputName();
		List<AnonVO> list = anonDAO.selectAll();
		String str = "";
		if (list == null) {
			System.out.println("게시글이 없습니다.");
		} else {
			for (AnonVO anonVO : list) {
				if (anonVO.getAnonName().equals(find)) {
					str += anonVO;
					;
				}
			}
		}
		System.out.println(find + "님의 게시물 >");
		System.out.println(str);
		System.out.println("--------------------------");
	}

	// 검색할 닉네임
	private String inputName() {
		System.out.println("검색할 닉네임을 입력해주세요 > ");
		String find = sc.nextLine();
		return find;
	}

	// 메뉴잘못선택시 출력
	private void error() {
		System.out.println("올바른 메뉴를 입려해 주세요");
		System.out.println();
	}
}
