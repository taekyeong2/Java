package com.yedam.project.board.anon;

import java.util.List;
import java.util.Scanner;

import com.yedam.project.board.anon.comment.AnonCommentDAO;
import com.yedam.project.board.anon.comment.AnonCommentDAOImpl;
import com.yedam.project.board.anon.comment.AnonCommentVO;

public class AnonManage {
	// 스캐너 호출
	Scanner sc = new Scanner(System.in);
	// 익명게시판과 익명댓글의 각각 싱글톤 호출
	AnonDAO anonDAO = AnonDAOImpl.getInstance();
	AnonCommentDAO anonCDAO = AnonCommentDAOImpl.getInstance();

	public AnonManage() {
		boolean run = true;
		while (run) {
			// 익명게시판 접속
			boardType();
			// 접속시 모든 게시판 글 호출
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
				int selectNum = boardNumInput();
				// 게시글 번호가 있는지 없는지 체크 후 참/거짓값 반환
				crun = selectCheck(selectNum);
				// 게시글이 있다면 while문 실행
				while (crun) {
					// 조회한 게시글 출력 - 앞에서 입력한 게시글 번호를 매개변수로
					anonSelect(selectNum);
					// 메뉴출력
					menuPrint();
					// 조회메뉴 선택
					menNo = menuSelect();
					switch (menNo) {
					case 1:
						// 수정 - 게시글번호를 매개변수
						anonUpdate(selectNum);
						break;
					case 2:
						// 삭제
						anonDelete(selectNum);
						// 삭제 완료시 뒤로가기
						crun = boardCheck(selectNum);
						break;
					case 3:
						// 댓글작성
						anonCWrite(selectNum);
						break;
					case 4:
						// 댓글수정
						anonCUpdate(selectNum);
						break;
					case 5:
						// 댓글삭제
						anonCDelete(selectNum);
						break;
					case 0:
						// 뒤로가기
						crun = false;
						break;
					default:
						// 없는 번호 작성시 오류 메세지
						error();
					}
				}
				break;
			case 3:
				// 검색
				anonSearch();
				break;
			case 0:
				// 뒤로가기
				run = false;
				break;
			default:
				// 없는 번호 작성시 오류 메세지
				error();

			}

		}
	}

	// 게시판이름 출력
	private void boardType() {
		System.out.println("< 익명게시판 >\n");
	}

	// 전체 게시글 불러오기
	private void anonSelectAll() {
		// 전체조회한 값을 담는다.
		List<AnonVO> anonVOList = anonDAO.selectAll();
		String str = "";
		// 향상된 for문을 통해 전체조회 값을 list(변수) 에담는다.
		for (AnonVO list : anonVOList) {
			// string으로 변환해서 배열출력할때 나오는 []대괄호 없애서 출력
			str += list;
		}
		System.out.println(str);
	}

	// 엄마메뉴 출력
	private void momMenuPrint() {
		System.out.println("=================================");
		System.out.println(" 1.작성 | 2.조회 | 3.검색 | 0.뒤로가기 ");
		System.out.println("=================================");
	}

	// 메뉴선택
	private int menuSelect() {
		System.out.println("메뉴를 선택해 주세요");
		int menNo = Integer.parseInt(sc.nextLine());
		return menNo;
	}

	// 게시글작성
	private void anonWrite() {
		// 입력받은 값을 DB에 넣어줌
		AnonVO anonVO = writeInfo();
		anonDAO.insert(anonVO);

	}

	// 게시글작성 내용입력
	private AnonVO writeInfo() {
		// 값을 입력해서 VO에 넣어줌
		AnonVO anonVO = new AnonVO();
		System.out.println("닉네임 > ");
		anonVO.setAnonName(sc.nextLine());
		System.out.println("비밀번호 > ");
		anonVO.setAnonPw(sc.nextLine());
		System.out.println("제목 > ");
		anonVO.setAnonTitle(sc.nextLine());
		System.out.println("내용 > ");
		anonVO.setAnonContent(sc.nextLine());

		return anonVO;
	}

	// 조회할 게시글 번호
	public int boardNumInput() {
		System.out.println("조회할 게시글 번호를 입력해 주세요.");
		System.out.println("게시글 번호 > ");
		int selectNum = Integer.parseInt(sc.nextLine());
		return selectNum;
	}

	// 게시글 단건 유무
	private boolean selectCheck(int anonNum) {
		boolean check = true;
		// 게시글 번호로 단건조회후 anonVO에 담아서
		AnonVO anonVO = anonDAO.selectOne(anonNum);
		if (anonVO == null) {
			// anonVO의 값이 없으면 출력
			System.out.println("없는 게시글 입니다.\n");
			check = false;
		}
		return check;
	}

	// 게시글 단건조회 + 딸린 댓글들 전체 조회
	private void anonSelect(int anonNum) {
		// 게시글 번호로 조회간 값을 변수에 넣어줌
		AnonVO anonVO = anonDAO.selectOne(anonNum);
		System.out.println(anonVO);
		// 게시글에 있는 댓글출력
		anonCoSelect(anonNum);

	}

	// 단건조회할 게시글에 있는 댓글들 전체조회
	private void anonCoSelect(int anonNum) {
		// list로 게시글 번호로 불러온 anonComment 테이블필드 값 넣어줌
		List<AnonCommentVO> list = anonCDAO.selectAll(anonNum);
		// String으로 변환해서 []없애줌
		String anonCStr = "";
		// 향상된for문 돌려서 리스트 값 VO변수에 담고
		for (AnonCommentVO comment : list) {
			// 앞에서 선언한 String에 더해줌
			anonCStr += comment;
		}
		System.out.println("\n" + "댓글 > ");
		System.out.println(anonCStr);
	}

	// 메뉴출력
	private void menuPrint() {
		System.out.println("=========================================================");
		System.out.println("1.수정 | 2.삭제 | 3.댓글작성 | 4.댓글수정 | 5.댓글삭제 | 0.뒤로가기");
		System.out.println("=========================================================");
	}

	// 게시글수정
	private void anonUpdate(int anonNum) {
		// 확인할 String 변수선언
		String confirm;
		// 본인이 맞으면
		if (check(anonNum) == true) {
			System.out.println("게시글 내용을 수정하시겠습니까? (y/n)");
			// y/n 값 입력
			confirm = sc.nextLine();
			// Y / y 상관없이 소문자로 인식 후 데이터 값이 맞는지 비교
			if (confirm.toLowerCase().equals("y")) {
				// 맞으면 수정
				String content = updateContent();
				anonDAO.update(anonNum, content);
			} else {
				// 아니면 메소드 종료
				return;
			}
		} else {
			// 본인이 아니면 출력
			System.out.println("비밀번호가 일치하지 않습니다.\n");
			return;
		}
	}

	// 본인확인(비번으로)
	private boolean check(int anonNum) {
		boolean check = false;
		System.out.println("비밀번호를 입력해주세요");
		String checkPw = sc.nextLine();
		// 게시물 번호로 단건조회
		AnonVO anonVO = anonDAO.selectOne(anonNum);
		// 입력한 패스워드와 단건조회한 패스워드값을 비교
		if (checkPw.equals(anonVO.getAnonPw())) {
			check = true;
		}
		return check;

	}

	// 수정할 내용 입력
	private String updateContent() {
		String content = null;
		System.out.println("내용 > ");
		content = sc.nextLine();
		return content;
	}

	// 게시글삭제+그 게시글 댓글전체 삭제
	private void anonDelete(int anonNum) {
		String confirm;
		// 본인이 맞으면
		if (check(anonNum) == true) {
			System.out.println("게시글 내용을 삭제하시겠습니까? (y/n)");
			confirm = sc.nextLine();
			// y소문자로 변환하고 입력한 값이 y이면
			if (confirm.toLowerCase().equals("y")) {
				// 선택한 게시물과 그 게시물에 있는 모든 댓글 삭제
				anonDAO.delete(anonNum);
				anonCDAO.deleteAll(anonNum);
			} else {
				return;
			}
		} else {
			// 본인이 아니면 출력
			System.out.println("비밀번호가 일치하지 않습니다.\n");
			return;
		}
	}

	// 게시글 유무 확인
	private boolean boardCheck(int anonNum) {
		boolean check = true;
		// 게시글 번호로 단건조회한값을 VO변수에 담아줌
		AnonVO anonVO = anonDAO.selectOne(anonNum);
		if (anonVO == null) {
			// 변수에 값이 없으면 false 값 반환
			check = false;
		}
		return check;
	}

	// 댓글작성
	private void anonCWrite(int anonNum) {
		AnonCommentVO anonCVO = writeCInfo(anonNum);
		// 작성한 내용을 DB에 담아줌
		anonCDAO.insert(anonCVO);
	}

	// 댓글작성내용
	private AnonCommentVO writeCInfo(int anonNum) {
		List<AnonCommentVO> checklist = anonCDAO.selectAll(anonNum);
		AnonCommentVO anonCVO = new AnonCommentVO();
		System.out.println("닉네임 > ");
		anonCVO.setAnonCName(sc.nextLine());
		System.out.println("비밀번호 > ");
		anonCVO.setAnonCPw(sc.nextLine());
		System.out.println("댓글 > ");
		anonCVO.setAnonCContent(sc.nextLine());
		//댓글의 게시글번호는 현재 선택한게시글 번호
		anonCVO.setAnonNum(anonNum);
		//새로다는 댓글번호 => 리스트크기에서 +1
		int num = checklist.size();
		++num;
		anonCVO.setAnonCNum(num);

		return anonCVO;
	}

	// 댓글수정
	private void anonCUpdate(int anonNum) {
		System.out.println("수정할 댓글번호를 입력해주세요");
		//수정할 댓글 번호 입력
		int anonCNum = anonCoInput();
		//댓글번호로 댓글 유무확인
		int checkCNum = commentCheck(anonNum, anonCNum);
		// 조회한 게시글 번호로 전체조회 후 VO리스트변수에 담아줌
		List<AnonCommentVO> anonCVO = anonCDAO.selectAll(anonNum);
		//향상된 for문 사용해 보변수에 담아줌
		for (AnonCommentVO anonCheck : anonCVO) {
			//댓글유무 누적값이 0보다 많으면 통과
			if (checkCNum > 0) {
				//같은 게시글의 댓글 번호들 중 입력한 댓글 번호와 맞는 것을
				if (anonCheck.getAnonCNum() == anonCNum) {
					String anonCPw = checkPw();
					//다시 입력한 비밀번호랑 가지고 있는 비밀번호를 비교해서
					if (anonCheck.getAnonCPw().equals(anonCPw)) {
						// 댓글수정
						String content = updateContent();
						anonCDAO.update(content, anonCNum, anonNum);
					} else {
						// 일치하지 않으면 출력
						System.out.println("비밀번호가 일치하지 않습니다.\n");
						return;
					}
				}
			} else{
				//댓글번호가 없으면
				System.out.println("댓글이 존재하지 않습니다.\n");
				return;
			}
		}
	}

	// 댓글 유무확인
	private int commentCheck(int anonNum, int anonCNum) {
		int checkCNum = 0;
		//게시글번호로 전체조회한 값을 리스트에 넣어줌
		List<AnonCommentVO> anonCVO = anonCDAO.selectAll(anonNum);
		//향상된 for문을 사용해서 변수에 넣어줌
		for (AnonCommentVO anonCheck : anonCVO) {
			//변수의 댓글번호와 입력한 댓글번호가 맞으면
			if (anonCheck.getAnonCNum() == anonCNum) {
				//댓글번호값을 누적해서 더해주기
				checkCNum += anonCheck.getAnonCNum();
			}
		}
		return checkCNum;

	}

	// 수정/삭제할 댓글 번호
	private int anonCoInput() {
		System.out.println("댓글번호 > ");
		int anonCNum = Integer.parseInt(sc.nextLine());
		return anonCNum;
	}

	// 댓글 비밀번호 확인
	private String checkPw() {
		System.out.println("비밀번호를 입력해주세요");
		String checkPw = sc.nextLine();
		return checkPw;

	}

	// 댓글삭제
	private void anonCDelete(int anonNum) {
		System.out.println("삭제할 댓글번호를 입력해주세요");
		//삭제할 댓글 입력
		int anonCNum = anonCoInput();
		//댓글번호로 댓글 유무 확인
		int checkCNum = commentCheck(anonNum, anonCNum);
		// 입력받은 댓글번호로 단건조회 후 VO변수에 담아줌
		List<AnonCommentVO> anonCVO = anonCDAO.selectAll(anonNum);
		//향상된 for문 사용
		for (AnonCommentVO anonCheck : anonCVO) {
			//댓글유무메소드의 누적 값이 0보다 크면
			if (checkCNum > 0) {
				//변수안에 들어간 댓글번호들중 입력한 댓글번호와 같으면
				if (anonCheck.getAnonCNum() == anonCNum) {
					String anonCPw = checkPw();
					//입력한 비밀번호와 변수의 비밀번호와 같으면
					if (anonCheck.getAnonCPw().equals(anonCPw)) {
						// 삭제
						anonCDAO.delete(anonCNum, anonNum);
					} else {
						// 일치하지 않으면 출력
						System.out.println("비밀번호가 일치하지 않습니다.\n");
						return;
					}

				}
			} else{
				//댓글번호에 해당하는 댓글이 없으면
				System.out.println("댓글이 존재하지 않습니다.\n");
				return;
			}
		}
	}

	// 글검색 - 닉네임으로
	private void anonSearch() {
		// 닉네임 입력받기
		String find = inputName();
		// 리스트에 모든 필드 호출해서 담기
		List<AnonVO> list = anonDAO.selectAll();
		String str = "";
		// 향상된 for문을 써서 리스트 내용을 VO변수에 담아줌
		for (AnonVO anonVO : list) {
			// 변수의 닉네임과 입력한 닉네임이 같으면
			if (anonVO.getAnonName().equals(find)) {
				// String 변수와 합치기(String값으로 변환해서 []없애주기 위함)
				str += anonVO;
			}

		}
		// 이때 앞의 string 변수가 비어있으면
		if (str.equals("")) {
			System.out.println("게시물이 없습니다.\n");
		} else { // string변수 값이 있으면
			System.out.println(find + "님의 게시물 >");
			System.out.println(str);
			System.out.println("--------------------------");
		}
	}

	// 검색할 닉네임
	private String inputName() {
		System.out.println("검색할 닉네임을 입력해주세요 > ");
		String find = sc.nextLine();
		return find;
	}

	// 메뉴잘못선택시 출력
	private void error() {
		System.out.println("올바른 메뉴를 입력해 주세요\n");
	}

}
