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
	public String getNoticeCContent() {
		return noticeCContent;
	}
	public void setNoticeCContent(String noticeCContent) {
		this.noticeCContent = noticeCContent;
	}
	public int getNoticeCNum() {
		return noticeCNum;
	}
	
	@Override
	public String toString() {
		return "게시글댓글 [ 작성자: " + memId + ", 댓글번호: " + noticeCNum
				+ ", 댓글: " + noticeCContent + "]";
	}
	
	
	
}
