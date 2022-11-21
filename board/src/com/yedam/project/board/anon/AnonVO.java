package com.yedam.project.board.anon;

public class AnonVO {
	//익명게시판 필드
	private String anonName; //닉네임
	private String anonPw; //패스워드
	private int anonNum; //게시글번호
	private String anonTitle; //게시글제목
	private String anonContent; //게시글내용

	//getter setter
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

	//게시판 번호 
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

	//toString
	@Override
	public String toString() {
		String result = null;
		//전체조회로 내용없이 제목까지만 출력할때
		if (anonContent == null) {
			result = "[" + anonNum + "] " + anonName + ") " + anonTitle + "\n";
		} else {   
			//단건조회로 내용까지 출력할때
			result = "[" + anonNum + "] " + anonName + ") " + anonTitle + "\n" + anonContent;
		}
		return result;
	}

}
