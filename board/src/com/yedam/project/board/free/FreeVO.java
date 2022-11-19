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



	public int getFreeNum() {
		int num = 0;
		this.freeNum = ++num;
		return freeNum;
	}



	public void setFreeNum(int freeNum) {
		this.freeNum = freeNum;
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



	@Override
	public String toString() {
		String result = null;
		if (freeContent == null) {
			result = "[" + freeNum + "] " + memId + ") " + freeTitle + "\n";
		} else {
			result = "[" + freeNum + "] " + memId + ") " + freeTitle + "\n" + freeContent;
		}
		return result;
	}
	
	
	
	
}
