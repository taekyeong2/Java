package com.yedam.app;

import java.util.List;
import java.util.Scanner;

import com.yedam.app.deptM.DeptDAO;
import com.yedam.app.deptM.DeptDAOImpl;
import com.yedam.app.deptM.DeptVO;

public class DeptManagement {
	Scanner sc = new Scanner(System.in);
	DeptDAO deptDAO = DeptDAOImpl.getInstance();

	public DeptManagement() {
		while (true) {
			// 메뉴출력
			menuPrint();
			// 메뉴선택
			int menuNo = menuSelect();
			// 각메뉴의 기능을 실행
			if (menuNo == 1) {
				// 전체조회
				selectAll();
			} else if (menuNo == 2) {
				// 단건조회
				selectOne();
			} else if (menuNo == 3) {
				// 사원등록
				insertEmp();
			} else if (menuNo == 4) {
				// 사원수정
				updateEmp();
			} else if (menuNo == 5) {
				// 사줭 삭제
				deleteEmp();
			} else if (menuNo == 9) {
				// 종료
				end();
				break;
			} else {
				// 기타사항
				printErrorMessage();
			}
		}

	}
	
	private void menuPrint() {
		System.out.println("=====================================================");
		System.out.println("==1.전체조회 2.사원조회 3.사원등록 4.사원수정 5.사원삭제 9.종료==");
		System.out.println("=====================================================");
	}
	
	private int menuSelect() {
		int menuNo = 0;
		try {
			menuNo = Integer.parseInt(sc.nextLine());
		}catch(NumberFormatException e) {
			System.out.println("메뉴는 숫자로 구성되어 있습니다.");
		}
		return menuNo;
	}
	
	private void selectAll() {
		List<DeptVO> list = deptDAO.selectAll();
		
		if(list.isEmpty()) {
			System.out.println("정보가 존재하지 않습니다.");
			return;
		}
		for(DeptVO deptVO : list) {
			System.out.printf("사원번호 %d: 부서 %s, (%s ~ %s) \n", deptVO.getEmpNo(), deptVO.getDeptNo(), deptVO.getFromDate(), deptVO.getToDate());
		}
	}
	
	private void selectOne() {
		int deptNo = inputDeptNo();
		DeptVO deptVO = deptDAO.selectOne(deptNo);
		if(deptVO == null) {
			System.out.printf("%d 사원의 정보가 없습니다.\n", deptNo);
		}else {
			System.out.println("검색결과> ");
			System.out.printf("사원번호 %d: 부서 %s, (%s ~ %s) \n", deptVO.getEmpNo(), deptVO.getDeptNo(), deptVO.getFromDate(), deptVO.getToDate());
		}
	}
	
	private void insertEmp() {
		DeptVO deptVO = inputDeptAll();
		deptDAO.insert(deptVO);
	}
	
	private void updateEmp() {
		DeptVO deptVO = inputDeptInfo();
		deptDAO.update(deptVO);
	}
	
	private void deleteEmp() {
		int deptNo = inputDeptNo();
		deptDAO.delete(deptNo);
	}
	
	private DeptVO inputDeptAll() {
		DeptVO deptVO = new DeptVO();
		System.out.println("부서번호(d---)> ");
		deptVO.setDeptNo(sc.nextLine());
		System.out.println("사원번호> ");
		deptVO.setEmpNo(Integer.parseInt(sc.nextLine()));
		System.out.println("입사날짜(yyyy-MM-dd)> ");
		deptVO.setFromDate(sc.nextLine());
		System.out.println("퇴사날짜(yyyy-MM-dd)> ");
		deptVO.setToDate(sc.nextLine());
		return deptVO;
	}
	
	private DeptVO inputDeptInfo() {
		DeptVO deptVO = new DeptVO();
		System.out.println("수정할 사원번호> ");
		deptVO.setEmpNo(Integer.parseInt(sc.nextLine()));
		System.out.println("부서번호(d---)> ");
		deptVO.setDeptNo(sc.nextLine());
		return deptVO;
	}
	
	private int inputDeptNo() {
		int deptNo = 0;
		try {
			System.out.println("사원번호> ");
			deptNo = Integer.parseInt(sc.nextLine());
		}catch(NumberFormatException e) {
			System.out.println("사원번호는 숫자로 입력해주세요");
		}
		return deptNo;
	}
	
	private void printErrorMessage() {
		System.out.println("=======================");
		System.out.println("==메뉴를 잘못 입력하였습니다.=");
		System.out.println("==메뉴를 다시 확인해주세요====");
		System.out.println("=======================");
	}
	
	private void end() {
		System.out.println("======================");
		System.out.println("===프로그램을 종료합니다.===");
		System.out.println("======================");
	}
	
}
