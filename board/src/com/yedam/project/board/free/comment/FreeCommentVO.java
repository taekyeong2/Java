package com.yedam.project.board.free.comment;

public class FreeCommentVO {
	private String memId;
	private int freeNum;
	private int freeCNum;
	private String freeCContent;
	
	
	
	public String getMemId() {
		return memId;
	}



	public void setMemId(String memId) {
		this.memId = memId;
	}



	public int getFreeNum() {
		return freeNum;
	}



	public void setFreeNum(int freeNum) {
		this.freeNum = freeNum;
	}



	public int getFreeCNum() {
		int num = 0;
		this.freeCNum = ++num;
		return freeCNum;
	}



	public void setFreeCNum(int freeCNum) {
		this.freeCNum = freeCNum;
	}



	public String getFreeCContent() {
		return freeCContent;
	}



	public void setFreeCContent(String freeCContent) {
		this.freeCContent = freeCContent;
	}



	@Override
	public String toString() {
		String result = null;
		if(memId == null) {
			result = "";
		}else {
		    result =  freeCNum + ") "+memId + ": " + freeCContent +"\n";
		}
		return result;
	}
	
	
	
	
}
