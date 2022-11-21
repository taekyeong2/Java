package com.yedam.project.board.anon.comment;


public class AnonCommentVO {
	//익명게시판 댓글 필드
	private String anonCName; //댓글 닉네임
	private String anonCPw; //댓글 패스워드
	private int anonNum; //게시글 번호
	private int anonCNum; //댓글번호
	private String anonCContent; //댓글내용
	
	
	
	
	
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
		String result = null;
		//댓글닉네임이 없을때 출력
		if(anonCName == null) {
			result = "";
		}else {
			//닉네임이 있을때 출력
		    result =  anonCNum + ") "+anonCName + ": " + anonCContent +"\n";
		}
		return result;
	}
	
	
	
	
	
}
