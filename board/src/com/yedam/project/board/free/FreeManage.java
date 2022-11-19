package com.yedam.project.board.free;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.yedam.project.board.common.LoginControl;
import com.yedam.project.board.free.comment.FreeCommentDAO;
import com.yedam.project.board.free.comment.FreeCommentDAOImpl;
import com.yedam.project.board.free.comment.FreeCommentVO;

public class FreeManage {
	Scanner sc = new Scanner(System.in);
	FreeDAO freeDAO = FreeDAOImpl.getInstance();
	FreeCommentDAO freeCDAO = FreeCommentDAOImpl.getInstance();
	String memId = LoginControl.getLoginInfo().getMemId();

	public FreeManage() {
		boolean run = true;
		while (run) {
			// 자유게시판접속
			freeSelectAll();
			// 메뉴출력
			momMenuPrint();
			// 메뉴선택
			int menNo = menuSelect();
			switch (menNo) {
			case 1:
				// 작성
				freeWrite();
				break;
			case 2:
				// 조회
				boolean crun = true;
				System.out.println("조회할 게시글 번호를 입력해 주세요.");
				System.out.println("게시글 번호 > ");
				int selectNum = Integer.parseInt(sc.nextLine());
				while (crun) {

					freeSelect(selectNum);
					// 메뉴출력
					menuPrint();
					menNo = menuSelect();
					// 조회메뉴 선택
					switch (menNo) {
					case 1:
						// 수정
						freeUpdate(selectNum);
						break;
					case 2:
						// 삭제
						freeDelete(selectNum);
						crun = false;
						break;
					case 3:
						// 댓글작성
						freeCWrite(selectNum);
						break;
					case 4:
						// 댓글수정
						freeCUpdate();
						break;
					case 5:
						// 댓글삭제
						freeCDelete();
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
				freeSearch();
				break;
			case 0:
				run = false;
				break;
			default:
				error();
			}
		}

	}

	// 전체 게시글 불러오기
	private void freeSelectAll() {
		List<FreeVO> freeVOList = freeDAO.selectAll();
		String str = "";
		for (FreeVO list : freeVOList) {
			str += list;
		}
		System.out.println(str);
	}

	// 엄마메뉴 출력
	private void momMenuPrint() {
		System.out.println("===========================");
		System.out.println("1.작성 |2.조회 |3.검색 |0.뒤로가기");
		System.out.println("===========================");
	}

	// 메뉴선택
	private int menuSelect() {
		System.out.println("메뉴를 선택해 주세요");
		int menNo = Integer.parseInt(sc.nextLine());
		return menNo;
	}

	// 게시글 작성
	private void freeWrite() {
		FreeVO freeVO = writeInfo();
		freeDAO.insert(freeVO);

	}

	// 게시글작성 내용입력
	private FreeVO writeInfo() {
		FreeVO freeVO = new FreeVO();
		freeVO.setMemId(memId);
		System.out.println("제목 > ");
		freeVO.setFreeTitle(sc.nextLine());
		System.out.println("내용 > ");
		freeVO.setFreeContent(sc.nextLine());

		return freeVO;
	}

	// 게시글 단건조회
	private void freeSelect(int freeNum) {
		FreeVO freeVO = freeDAO.selectOne(freeNum);
		List<FreeCommentVO> list = freeCDAO.selectAll(freeNum);
		String freeStr = "";
		String freeCStr = "";
		if (freeVO == null) {
			System.out.println("없는 게시글 입니다.");
		} else {
			freeStr += freeVO;
			for (FreeCommentVO comment : list) {
				freeCStr += comment;
			}
			System.out.println(freeStr);
			System.out.println("\n" + "댓글 > ");
			System.out.println(freeCStr);
		}
	}

	// 메뉴출력
	private void menuPrint() {
		System.out.println("==============================================");
		System.out.println("1.수정 |2.삭제 |3.댓글작성 |4.댓글수정 |5.댓글삭제 |0.뒤로가기");
		System.out.println("==============================================");
	}

	// 게시글 수정
	private void freeUpdate(int freeNum) {
		String confirm;
		System.out.println("게시글 내용을 수정하시겠습니까? (y/n)");
		confirm = sc.nextLine();

		if (confirm.toLowerCase().equals("y")) {
			boolean anonCheck = check(freeNum);
			if (anonCheck = true) {
				String content = updateContent();
				freeDAO.update(freeNum, content);
			} else {
				System.out.println("작성하신 글이 아닙니다.");
				return;
			}
		} else {
			return;
		}
	}

	// 본인확인 - 아이디일치 확인
	private boolean check(int freeNum) {
		boolean check = false;
		FreeVO freeVO = freeDAO.selectOne(freeNum);
		if (freeVO.getMemId().equals(memId)) {
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

	// 게시글삭제 + 그게시글의 전체 댓글삭제
	private void freeDelete(int freeNum) {
		String confirm;
		System.out.println("게시글 내용을 삭제하시겠습니까? (y/n)");
		confirm = sc.nextLine();

		if (confirm.toLowerCase().equals("y")) {
			boolean anonCheck = check(freeNum);
			if (anonCheck = true) {
				freeDAO.delete(freeNum);
				freeCDAO.deleteAll(freeNum);
			} else {
				System.out.println("작성하신 글이 아닙니다.");
				return;
			}
		} else {
			return;
		}
	}

	// 댓글작성
	private void freeCWrite(int freeNum) {
		FreeCommentVO freeCVO = writeCInfo(freeNum);
		freeCDAO.insert(freeCVO);
	}

	// 댓글작성내용
	private FreeCommentVO writeCInfo(int freeNum) {
		FreeCommentVO freeCVO = new FreeCommentVO();
		freeCVO.setMemId(memId);
		System.out.println("댓글 > ");
		freeCVO.setFreeCContent(sc.nextLine());
		freeCVO.setFreeNum(freeNum);

		return freeCVO;
	}

	// 댓글수정
	private void freeCUpdate() {
		System.out.println("수정할 댓글번호를 입력해주세요");
		int freeCNum = freeCoInput();
		boolean freeCheck = checkC(freeCNum);
		if (freeCheck = true) {
			String content = updateContent();
			freeCDAO.update(freeCNum, content);
		} else {
			System.out.println("본인이 아닙니다.");
			return;
		}
	}

	// 댓글삭제 시 아이디 비교
	private boolean checkC(int freeCNum) {
		boolean checkC = false;
		List<FreeCommentVO> freeCVO = new ArrayList<>();
		freeCVO = freeCDAO.selectAll(freeCNum);
		for (FreeCommentVO find : freeCVO) {
			if (find.getMemId().equals(memId)) {
				checkC = true;
			}
		}
		return checkC;
	}

	// 수정할 댓글 번호
	private int freeCoInput() {
		System.out.println("댓글번호 > ");
		int freeCNum = Integer.parseInt(sc.nextLine());
		return freeCNum;
	}
	
	//댓글삭제
	private void freeCDelete() {
		System.out.println("삭제할 댓글번호를 입력해주세요");
		int freeCNum = freeCoInput();
		boolean freeCheck = checkC(freeCNum);
		if (freeCheck = true) {
			freeCDAO.delete(freeCNum);
		} else {
			System.out.println("본인이 아닙니다.");
			return;
		}
	}
	
	//글검색 - 아이디로 
	private void freeSearch() {
		List<FreeVO> list = freeDAO.selectAll();
		String str = "";
		if (list == null) {
			System.out.println("게시글이 없습니다.");
		} else {
			for(FreeVO freeVO : list) {
				if(freeVO.getMemId().equals(memId)) {
					str += freeVO;;
				}
			}
		}
		System.out.println(memId+"님의 게시물 >");
		System.out.println(str);
		System.out.println("--------------------------");
	}
	
	//메뉴잘못선택시 출력
	private void error() {
			System.out.println("올바른 메뉴를 입려해 주세요");			
	}

	
}
