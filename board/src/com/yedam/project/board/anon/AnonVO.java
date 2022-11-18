package com.yedam.project.board.anon;

public class AnonVO {
	private String anonName;
	private String anonPw;
	private int anonNum;
	private String anonTitle;
	private String anonContent;
	
	
	
	public String getAnonName() {
		return anonName;
	}



	public void setAnonName(String anonName) {
		this.anonName = anonName;
	}



	public String getAnonPw() {
		return anonPw;
	}



	public void setAnonPw(String anonPw) {
		this.anonPw = anonPw;
	}



	public int getAnonNum() {
		return anonNum;
	}



	public void setAnonNum(int anonNum) {
		this.anonNum = anonNum;
	}



	public String getAnonTitle() {
		return anonTitle;
	}



	public void setAnonTitle(String anonTitle) {
		this.anonTitle = anonTitle;
	}



	public String getAnonContent() {
		return anonContent;
	}



	public void setAnonContent(String anonContent) {
		this.anonContent = anonContent;
	}



	@Override
	public String toString() {
		return "익명게시판 [ 닉네임: " + anonName + ", 게시글번호: " + anonNum + ", 제목: "
				+ anonTitle + ", 내용: " + anonContent + "]";
	}
	
	
	
}
