package com.yedam.project.board.notice;

public class NoticeVO {
	private String memId;
	private int noticeNum;
	private String noticeTitle;
	private String noticeContent;
	
	
	
	
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




	public String getNoticeTitle() {
		return noticeTitle;
	}




	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}




	public String getNoticeContent() {
		return noticeContent;
	}




	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}




	@Override
	public String toString() {
		return "공지게시판 [ 게시글번호: " + noticeNum + ", 제목: " + noticeTitle
				+ ", 내용: " + noticeContent + "]";
	}
	
	
	
	

}
