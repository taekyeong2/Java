package com.yedam.project.board.notice.comment;

public class NoticeCommentVO {
	private String memId;
	private int noticeNum;
	private int noticeCNum; 
	private String noticeCContent;
	
	
	
	
	public String getMemId() {
		return memId;
	}




	public void setMemId(String memId) {
		this.memId = memId;
	}




	public int getNoticeNum() {
		return noticeNum;
	}




	public void setNoticeNum(int noticeNum) {
		this.noticeNum = noticeNum;
	}




	public int getNoticeCNum() {
		return noticeCNum;
	}




	public void setNoticeCNum(int noticeCNum) {
		this.noticeCNum = noticeCNum;
	}




	public String getNoticeCContent() {
		return noticeCContent;
	}




	public void setNoticeCContent(String noticeCContent) {
		this.noticeCContent = noticeCContent;
	}




	@Override
	public String toString() {
		String result = null;
		if(memId == null) {
			result = "";
		}else {
		    result =  noticeCNum + ") "+memId + ": " + noticeCContent +"\n";
		}
		return result;
	}
	
	
	
}
