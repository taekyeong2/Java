package com.yedam.project.board.member;

public class MemberVO {
	private String memId;
	private String memPw;
	private int memRole;
	
	
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemPw() {
		return memPw;
	}
	public void setMemPw(String memPw) {
		this.memPw = memPw;
	}
	public int getMemRole() {
		return memRole;
	}
	public void setMemRole(int memRole) {
		this.memRole = memRole;
	}
	
	@Override
	public String toString() {
		String idInfo = "";
		if(memRole == 0) {
			idInfo = "관리자 : "+ memId;
		}else {
			idInfo = "일반회원 : "+memId;
		}
		return idInfo;
	}
	
	
	
	
	
}
