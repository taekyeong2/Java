package com.yedam.project.board.free;

public class FreeVO {
	private String memId;
	private int freeNum;
	private String freeTitle;
	private String freeContent;
	
	
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getFreeTitle() {
		return freeTitle;
	}
	public void setFreeTitle(String freeTitle) {
		this.freeTitle = freeTitle;
	}
	public String getFreeContent() {
		return freeContent;
	}
	public void setFreeContent(String freeContent) {
		this.freeContent = freeContent;
	}
	public int getFreeNum() {
		return freeNum;
	}
	@Override
	public String toString() {
		return "자유게시판 [ 작성자: " + memId + ", 게시글번호: " + freeNum + ", 제목: " + freeTitle + ", 내용: "
				+ freeContent + "]";
	}
	
	
	
	
}
