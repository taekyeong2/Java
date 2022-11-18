package com.yedam.project.board.anon.comment;

public class AnonCommentVO {
	private String anonCName;
	private String anonCPw;
	private int anonNum;
	private int anonCNum;
	private String anonCContent;
	
	
	
	
	
	public String getAnonCName() {
		return anonCName;
	}





	public void setAnonCName(String anonCName) {
		this.anonCName = anonCName;
	}





	public String getAnonCPw() {
		return anonCPw;
	}





	public void setAnonCPw(String anonCPw) {
		this.anonCPw = anonCPw;
	}





	public int getAnonNum() {
		return anonNum;
	}





	public void setAnonNum(int anonNum) {
		this.anonNum = anonNum;
	}





	public int getAnonCNum() {
		return anonCNum;
	}





	public void setAnonCNum(int anonCNum) {
		this.anonCNum = anonCNum;
	}





	public String getAnonCContent() {
		return anonCContent;
	}





	public void setAnonCContent(String anonCContent) {
		this.anonCContent = anonCContent;
	}





	@Override
	public String toString() {
		return "익명게시판 댓글 [ 닉네임: " + anonCName + ", 댓글번호: "
				+ anonCNum + ", 댓글: " + anonCContent + "]";
	}
	
	
	
	
	
}
